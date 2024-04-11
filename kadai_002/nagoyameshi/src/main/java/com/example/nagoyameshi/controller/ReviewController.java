package com.example.nagoyameshi.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/stores/{id}/reviews")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final StoreRepository storeRepository;
	private final ReviewService reviewService;
	
	public ReviewController(ReviewRepository reviewRepository, StoreRepository storeRepository, ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.storeRepository = storeRepository;
		this.reviewService = reviewService;
	}
	
	@GetMapping("/edit")
	public String edit(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Review review = reviewRepository.findByUserIdAndStoreId(userDetailsImpl.getUser().getId(), id);
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getNumberOfStar(), review.getCommentTitle(), review.getCommentContent());
		
		Store store = storeRepository.getReferenceById(id);
		
		model.addAttribute("store", store);
		model.addAttribute("reviewEditForm", reviewEditForm);
		
		return "reviews/edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated ReviewEditForm reviewEditForm,
						 BindingResult bindingResult,
						 @PathVariable(name = "id") Integer id,
						 RedirectAttributes redirectAttributes,
						 Model model) 
	{
		if (bindingResult.hasErrors()) {
			Store store = storeRepository.getReferenceById(id);
			
			model.addAttribute("store", store);
			model.addAttribute("reviewEditForm", reviewEditForm);
			
			return "reviews/edit";
		}
		
		reviewService.update(reviewEditForm);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを更新しました。");
		return "redirect:/stores/{id}";
	}
	
	@PostMapping("/delete")
	public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer storeId, RedirectAttributes redirectAttributes) {
		reviewService.delete(userDetailsImpl.getUser().getId(), storeId);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
		return "redirect:/stores/{id}";
	}
	
}
