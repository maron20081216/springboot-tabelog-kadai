package com.example.nagoyameshi.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.ReviewService;
import com.example.nagoyameshi.service.StoreService;

@Controller
@RequestMapping("/stores")
public class StoreController {
	private final StoreRepository storeRepository;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final StoreService storeService;
	private final FavoriteService favoriteService;
	
	public StoreController(StoreRepository storeRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository, ReviewService reviewService, StoreService storeService, FavoriteService favoriteService) {
		this.storeRepository = storeRepository;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
		this.storeService = storeService;
		this.favoriteService = favoriteService;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
						@RequestParam(name = "categoryId", required = false) Integer categoryId,
						@RequestParam(name = "price", required = false) Integer price,
						@RequestParam(name = "order", required = false) String order,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model)
	{
		Page<Store> storePage;
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		if (keyword != null && !keyword.isEmpty()) {
			if (order != null && order.equals("princeDesc")) {
				storePage = storeRepository.findKeywordExpensiveOrder("%" + keyword + "%","%" + keyword + "%", "%" + keyword + "%", pageable);
			} else if (order != null && order.equals("princeAsc")) {
				storePage = storeRepository.findKeywordCheapOrder("%" + keyword + "%","%" + keyword + "%", "%" + keyword + "%", pageable);
			} else {
				storePage = storeRepository.findKeywordNewOrder("%" + keyword + "%","%" + keyword + "%", "%" + keyword + "%", pageable);
			}
		} else if (categoryId != null) {
			if (order != null && order.equals("princeDesc")) {
				storePage = storeRepository.findCategoryExpensiveOrder(categoryId, pageable);
			} else if (order != null && order.equals("princeAsc")) {
				storePage = storeRepository.findCategoryCheapOrder(categoryId, pageable);
			} else {
				storePage = storeRepository.findCategoryNewOrder(categoryId, pageable);
			}
		} else if (price != null) {
			if (order != null && order.equals("princeDesc")) {
				storePage = storeRepository.findMaxPriceExpensiveOrder(price, pageable);
			} else if (order != null && order.equals("princeAsc")) {
				storePage = storeRepository.findMaxPriceCheapOrder(price, pageable);
			} else {
				storePage = storeRepository.findMaxPriceNewOrder(price, pageable);
			}
		} else {
			if (order != null && order.equals("princeDesc")) {
				storePage = storeRepository.findAllExpensiveOrder(pageable);
			} else if (order != null && order.equals("princeAsc")) {
				storePage = storeRepository.findAllCheapOrder(pageable);
			} else {
				storePage = storeRepository.findAllNewOrder(pageable);
			}
		}

		
		List<Store> storeList = storeRepository.findAll();
		HashMap<Integer, Double> reviewAverageMap = new HashMap<Integer, Double>();
		HashMap<Integer, Integer> reviewSumMap = new HashMap<Integer, Integer>();

		for( int i = 0; i < storeList.size(); i++) {
			Integer storeId = storeList.get(i).getId();
			double starAverage = reviewService.starAverage(storeId);
			reviewAverageMap.put(storeId, starAverage);
		}

		for( int i = 0; i < storeList.size(); i++) {
			Integer storeId = storeList.get(i).getId();
			List<Review> storeReviewList = reviewRepository.findByStoreIdOrderByCreatedAtDesc(storeId);
			Integer sum = storeReviewList.size();
			reviewSumMap.put(storeId, sum);
		}

		model.addAttribute("reviewAverageMap", reviewAverageMap);	
		model.addAttribute("reviewSumMap", reviewSumMap);	
		model.addAttribute("storePage", storePage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("price", price);
		model.addAttribute("order", order);
		
		return "stores/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Store store = storeRepository.getReferenceById(id);
		model.addAttribute("store", store);
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		
		model.addAttribute("favoriteCheck", favoriteService.favoriteCheck(userDetailsImpl.getUser().getId(), store.getId()));
		model.addAttribute("reviewCheck", reviewService.reviewCheck(userDetailsImpl.getUser().getId(), store.getId()));
		
		List<Review> reviewList = reviewRepository.findByStoreIdAndUserIdNotOrderByCreatedAtDesc(store.getId(), userDetailsImpl.getUser().getId());
		model.addAttribute("reviewList", reviewList);
		
		Review userReview = reviewRepository.findByUserIdAndStoreId(userDetailsImpl.getUser().getId(), id);
		model.addAttribute("userReview", userReview);
		
		double starAverage = reviewService.starAverage(id);
		model.addAttribute("starAverage", starAverage);

		return "stores/show";
	}
	
	@PostMapping("/{id}/reviews")
	public String review(@PathVariable(name = "id") Integer id, 
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, 
						 @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes,
						 Model model)
	{
		Store store = storeRepository.getReferenceById(id);

		if (bindingResult.hasErrors()) {
			// エラーでshowページに戻すから、showページを初期化する
			model.addAttribute("store", store);
			model.addAttribute("reservationInputForm", new ReservationInputForm());
			model.addAttribute("reviewRegisterForm", reviewRegisterForm);
			
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
			model.addAttribute("errorMessage", "レビュー内容に不備があります。");
			return "stores/show";
		}
		
		reviewService.create(userDetailsImpl.getUser(), store, reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");
		
		return "redirect:/stores/{id}";
	}
	
	@GetMapping("/{id}/favorites/delete")
	public String favoriteDelete(@PathVariable(name = "id") Integer id,
								 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
								 RedirectAttributes redirectAttributes)
	{
		Store store = storeRepository.getReferenceById(id);

		storeService.favoriteOff(userDetailsImpl.getUser().getId(), store.getId());
		
		redirectAttributes.addFlashAttribute("successMessage", "お気に入り登録を解除しました。");
		return "redirect:/stores/{id}";		
	}
	
	@GetMapping("/{id}/favorites/create")
	public String favoriteCreate(@PathVariable(name = "id") Integer id,
			 					 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			 					 RedirectAttributes redirectAttributes)
	{
		Store store = storeRepository.getReferenceById(id);
		storeService.favoriteOn(userDetailsImpl.getUser(), store);
		
		redirectAttributes.addFlashAttribute("successMessage", "お気に入り登録しました。");
		return "redirect:/stores/{id}";		
	}

}
