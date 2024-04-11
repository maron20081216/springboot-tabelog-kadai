package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Profile;
import com.example.nagoyameshi.entity.Template;
import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.form.ProfileEditForm;
import com.example.nagoyameshi.form.TemplateEditForm;
import com.example.nagoyameshi.form.TermEditForm;
import com.example.nagoyameshi.repository.ProfileRepository;
import com.example.nagoyameshi.repository.TemplateRepository;
import com.example.nagoyameshi.repository.TermRepository;
import com.example.nagoyameshi.service.SettingService;

@Controller
@RequestMapping("admin/settings")
public class AdminSettingController {
	private final ProfileRepository profileRepository;
	private final TermRepository termRepository;
	private final TemplateRepository templateRepository;
	private final SettingService settingService;
	
	public AdminSettingController(ProfileRepository profileRepository, TermRepository termRepository, TemplateRepository templateRepository, SettingService settingService) {
		this.profileRepository = profileRepository;
		this.settingService = settingService;
		this.termRepository = termRepository;
		this.templateRepository = templateRepository;
	}
	
	// 会社概要設定
	@GetMapping("/profile")
	public String profileIndex(Model model) {
		Profile profile = profileRepository.getReferenceById(1);
		
		model.addAttribute("profile", profile);
		
		return "admin/settings/profile/index";
	}

	@GetMapping("/profile/edit")
	public String profileEdit(Model model) {
		Profile profile = profileRepository.getReferenceById(1);
		ProfileEditForm profileEditForm = new ProfileEditForm(profile.getId(), profile.getName(), profile.getPresident(), profile.getBirthday(), profile.getPostalCode(), profile.getAddress(), profile.getBusiness());
		
		model.addAttribute("profileEditForm", profileEditForm);
		
		return "admin/settings/profile/edit";
	}
	
	@PostMapping("/profile/update")
	public String profileUpdate(@ModelAttribute @Validated ProfileEditForm profileEditForm, BindingResult bindingResult, RedirectAttributes redirestAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			return "admin/settings/profile/edit";
		}
		
		settingService.profileUpdate(profileEditForm);
		redirestAttributes.addFlashAttribute("successMessage", "会社概要を更新しました。");
		
		return "redirect:/admin/settings/profile";
	}
	
	// 利用規約設定
	@GetMapping("/terms")
	public String termsIndex(Model model) {
		Term term = termRepository.getReferenceById(1);
		
		// テキスト中の改行を<br>タグに置換
		String formattedCotent = term.getContent().replace("\n", "<br>");
		term.setContent(formattedCotent);

		model.addAttribute("term", term);
		return "admin/settings/terms/index";
	}
	
	@GetMapping("/terms/edit")
	public String termsEdit(Model model) {
		Term term = termRepository.getReferenceById(1);
		TermEditForm termEditForm = new TermEditForm(term.getId(), term.getContent());
		
		model.addAttribute("termEditForm", termEditForm);
		
		return "admin/settings/terms/edit";
	}
	
	@PostMapping("/terms/update")
	public String termsUpdate(@ModelAttribute @Validated TermEditForm termEditForm, BindingResult bindingResult, RedirectAttributes redirestAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			return "admin/setting/terms/edit";
		}
		
		settingService.termUpadate(termEditForm);
		redirestAttributes.addFlashAttribute("successMessage", "利用規約を更新しました。");
		
		return "redirect:/admin/settings/terms";
	}

	
	// メールテンプレート設定
	@GetMapping("/templates")
	public String templatesIndex(Model model) {
		Template template = templateRepository.getReferenceById(1);
		
		// テキスト中の改行を<br>タグに置換
		String formattedTemplate = template.getTemplate().replace("\n", "<br>");
		template.setTemplate(formattedTemplate);

		model.addAttribute("template", template);
		return "admin/settings/templates/index";
	}
	
	@GetMapping("/templates/edit")
	public String templatesEdit(Model model) {
		Template template = templateRepository.getReferenceById(1);
		TemplateEditForm templateEditForm = new TemplateEditForm(template.getId(), template.getName(), template.getSubject(), template.getTemplate());
		
		model.addAttribute("templateEditForm", templateEditForm);
		
		return "admin/settings/templates/edit";
	}
	
	@PostMapping("/templates/update")
	public String templatesUpdate(@ModelAttribute @Validated TemplateEditForm templateEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			return "admin/settings/templates/edit";
		}
		
		settingService.templateUpdate(templateEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "メールテンプレートを更新しました。");
		
		return "redirect:/admin/settings/templates";
	}

}
