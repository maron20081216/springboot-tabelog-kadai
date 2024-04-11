package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.example.nagoyameshi.form.StoreEditForm;
import com.example.nagoyameshi.form.StoreRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.service.ReviewService;
import com.example.nagoyameshi.service.StoreService;

@Controller
@RequestMapping("/admin/stores")
public class AdminStoreController {
	private final StoreRepository storeRepository;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final StoreService storeService;
	private final ReviewService reviewService;
	
	public AdminStoreController(StoreRepository storeRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository, StoreService storeService, ReviewService reviewService) {
		this.storeRepository = storeRepository;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.storeService = storeService;
		this.reviewService = reviewService;
	}
	
	@GetMapping
	public String index(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword, Model model) {
		Page<Store> storePage;
		
		if (keyword != null && !keyword.isEmpty()) {
			storePage = storeRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			storePage = storeRepository.findAll(pageable);
		}
		
		model.addAttribute("storePage", storePage);
		model.addAttribute("keyword", keyword);
		
		return "admin/stores/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model, Pageable pageable) {
		Store store = storeRepository.getReferenceById(id);
		model.addAttribute("store", store);
		
		List<Review> reviewList = reviewRepository.findByStoreIdOrderByCreatedAtDesc(store.getId());
		model.addAttribute("reviewList", reviewList);

		return "admin/stores/show";
	}
	
	@PostMapping("/{storeId}/review/{reviewId}/delete")
	public String delete(@PathVariable(name = "storeId") Integer storeId, @PathVariable(name = "reviewId") Integer reviewId, RedirectAttributes redirectAttributes) {
		Review review = reviewRepository.getReferenceById(reviewId);
		reviewService.delete(review.getUser().getId(), storeId);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
		return "redirect:/admin/stores/{storeId}";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		List<Category> categories = categoryRepository.findAll();
		
		model.addAttribute("storeRegisterForm", new StoreRegisterForm());
		model.addAttribute("categories", categories);
		
		return "admin/stores/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated StoreRegisterForm storeRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("categories", categories);

			return "admin/stores/register";
		}
		
		storeService.create(storeRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");
		
		return "redirect:/admin/stores";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		Store store = storeRepository.getReferenceById(id);
		String imageName = store.getImageName();
		StoreEditForm storeEditForm = new StoreEditForm(store.getId(), store.getName(), store.getCategory(), null, store.getDescription(), store.getMinPrice(), store.getMaxPrice(), store.getPostalCode(), store.getAddress(), store.getPhoneNumber(), store.getOpenTime(), store.getCloseTime(), store.getHoliday(), store.getCapacity(), store.getSearchKeyword(), store.getPriority());
		
		List<Category> categories = categoryRepository.findAll();

		model.addAttribute("imageName", imageName);
		model.addAttribute("storeEditForm", storeEditForm);
		model.addAttribute("categories", categories);
		
		return "admin/stores/edit";
	}
	
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated StoreEditForm storeEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			List<Category> categories = categoryRepository.findAll();
			model.addAttribute("categories", categories);
			
			return "admin/stores/edit";
		}
		
		storeService.update(storeEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を更新しました。");
		
		return "redirect:/admin/stores";
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		storeRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
		
		return "redirect:/admin/stores";
	}
}