package com.example.nagoyameshi.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.event.ReservationMailEventPublisher;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.ReservationService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;
	private final StoreRepository storeRepository;
	private final ReviewRepository reviewRepository;
	private final ReservationService reservationService;
	private final ReviewService reviewService;
	private final FavoriteService favoriteService;
	private final ReservationMailEventPublisher reservationMailEventPublisher;
	
	public ReservationController(ReservationRepository reservationRepository, StoreRepository storeRepository, ReservationService reservationService, ReviewService reviewService, FavoriteService favoriteService, ReviewRepository reviewRepository, ReservationMailEventPublisher reservationMailEventPublisher) {
		this.reservationRepository = reservationRepository;
		this.storeRepository = storeRepository;
		this.reviewRepository = reviewRepository;
		this.reservationService = reservationService;
		this.reviewService = reviewService;
		this.favoriteService = favoriteService;
		this.reservationMailEventPublisher = reservationMailEventPublisher;
	}
	
	@GetMapping("/reservations")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		User user = userDetailsImpl.getUser();
		Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		
		model.addAttribute("reservationPage", reservationPage);
		
		return "reservations/index";
	}
	
	@GetMapping("/reservations/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer storeId,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						 RedirectAttributes redirectAttributes,
						 Model model) 
	{
		User user = userDetailsImpl.getUser();
		
		// 予約当日以降の場合は、キャンセル不可というエラーにする
		Reservation reservation = reservationRepository.findByUserIdAndStoreId(user.getId(), storeId);
		LocalDate reservationDate = reservation.getReservationDate();
		LocalDate nowDate = LocalDate.now();
		
		if (reservationDate.compareTo(nowDate) <= 0) {
			model.addAttribute("errorMessage", "予約日当日以降はキャンセルできません。店舗様へ直接ご連絡ください。");
			
			Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
			model.addAttribute("reservationPage", reservationPage);

			return "reservations/index";
		}
		
		reservationService.cancel(user.getId(), storeId);
		
		redirectAttributes.addFlashAttribute("successMessage", "予約をキャンセルしました。");
		return "redirect:/reservations";
	}	
	
	@PostMapping("/stores/{id}/reservations/input")
	public String input(@PathVariable(name = "id") Integer id,
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						@ModelAttribute @Validated ReservationInputForm reservationInputForm,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						Model model) 
	{
		Store store = storeRepository.getReferenceById(id);
		Integer numberOfPeople = reservationInputForm.getNumberOfPeople();
		Integer capacity = store.getCapacity();
		
		if (numberOfPeople != null) {
			if (!reservationService.isWithinCapacity(numberOfPeople, capacity)) {
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "予約人数が座席数を超えています。");
				bindingResult.addError(fieldError);
			}
		}
		
		// 予約日時を制御する
		LocalDate reservationDate = reservationInputForm.getReservationDate();
		LocalTime reservationTime = reservationInputForm.getReservationTime();
		
		LocalTime openTime = store.getOpenTime();
		LocalTime closeTime = store.getCloseTime();
		
		LocalDate nowDate = LocalDate.now();
		LocalTime nowTime = LocalTime.now();

		if (reservationTime != null) {
			if ((reservationDate.compareTo(nowDate) == 0) && (reservationTime.compareTo(nowTime) < 0)) {
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "reservationDateAndTime", "予約日時が過去です。"); //予約日が当日＆現在時刻前ならエラーとする
				bindingResult.addError(fieldError);
			} else if ((reservationTime.compareTo(openTime) < 0) || (reservationTime.compareTo(closeTime) >= 0)) {  //予約時間が営業時間外ならエラーとする
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "reservationDateAndTime", "予約時間が営業時間外です。");
				bindingResult.addError(fieldError);
			}
		}
		
		if (bindingResult.hasErrors()) {
			// エラーでshowページに戻すから、showページを初期化する
			model.addAttribute("store", store);
			model.addAttribute("reservationInputForm", reservationInputForm);
			model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
			
			model.addAttribute("favoriteCheck", favoriteService.favoriteCheck(userDetailsImpl.getUser().getId(), store.getId()));
			model.addAttribute("reviewCheck", reviewService.reviewCheck(userDetailsImpl.getUser().getId(), store.getId()));
			
			List<Review> reviewList = reviewRepository.findByStoreIdAndUserIdNotOrderByCreatedAtDesc(store.getId(), userDetailsImpl.getUser().getId());
			model.addAttribute("reviewList", reviewList);
			
			Review userReview = reviewRepository.findByUserIdAndStoreId(userDetailsImpl.getUser().getId(), id);
			model.addAttribute("userReview", userReview);

			if (reviewList.size() > 0) {
				double starAverage = reviewService.starAverage(id);
				model.addAttribute("starAverage", starAverage);
			}

			// エラーメッセージを返して、showページに戻す
			model.addAttribute("errorMessage", "予約内容に不備があります。");
			return "stores/show";
		}
		
		redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
		return "redirect:/stores/{id}/reservations/confirm";
	}
	
	@GetMapping("/stores/{id}/reservations/confirm")
	public String confirm(@PathVariable(name = "id") Integer id,
						  @ModelAttribute ReservationInputForm reservationInputForm,
						  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						  Model model) 
	{
		Store store = storeRepository.getReferenceById(id);
		User user = userDetailsImpl.getUser();
		
		// 予約日と予約時間を取得する
		LocalDate reservationDate = reservationInputForm.getReservationDate();
		LocalTime reservationTime = reservationInputForm.getReservationTime();
		
		ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(user.getId(), store.getId(), reservationDate, reservationTime, reservationInputForm.getNumberOfPeople());
		
		model.addAttribute("store", store);
		model.addAttribute("reservationRegisterForm", reservationRegisterForm);
		
		return "reservations/confirm";
	}
	
	@PostMapping("/stores/{id}/reservations/create")
	public String create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute ReservationRegisterForm reservationRegisterForm) {
		reservationService.create(reservationRegisterForm);
		
		reservationMailEventPublisher.publishReservationMailEvent(userDetailsImpl.getUser());
		
		return "redirect:/reservations?reserved";
	}
	
}
