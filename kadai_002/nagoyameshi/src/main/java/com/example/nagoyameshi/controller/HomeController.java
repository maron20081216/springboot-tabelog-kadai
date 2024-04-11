package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.StoreRepository;

@Controller
public class HomeController {
	private final StoreRepository storeRepository;
	private final CategoryRepository categoryRepository;
	
	public HomeController(StoreRepository storeRepository, CategoryRepository categoryRepository) {
		this.storeRepository = storeRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/")
	public String customerIndex(Model model) {
		List<Store> newStores = storeRepository.findTop10ByOrderByPriority();
		model.addAttribute("newStores", newStores);
		
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "index";
	}

}
