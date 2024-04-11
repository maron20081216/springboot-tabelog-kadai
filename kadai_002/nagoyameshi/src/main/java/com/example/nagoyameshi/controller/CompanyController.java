package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Profile;
import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.repository.ProfileRepository;
import com.example.nagoyameshi.repository.TermRepository;

@Controller
public class CompanyController {
	private final ProfileRepository profileRepository;
	private final TermRepository termRepository;
	
	public CompanyController(ProfileRepository profileRepository, TermRepository termRepository) {
		this.profileRepository = profileRepository;
		this.termRepository = termRepository;
	}

	
	@GetMapping("/profile")
	public String profile(Model model) {
		Profile profile = profileRepository.getReferenceById(1);
		
		model.addAttribute("profile", profile);
		
		return "company/profile";
	}

	@GetMapping("/terms")
	public String terms(Model model) {
		Term term = termRepository.getReferenceById(1);
		
		// テキスト中の改行を<br>タグに置換
		String formattedCotent = term.getContent().replace("\n", "<br>");
		term.setContent(formattedCotent);

		model.addAttribute("term", term);
		
		return "company/terms";
	}

}
