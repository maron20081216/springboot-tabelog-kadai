package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.event.ChangeEmailEventPublisher;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;
import com.example.nagoyameshi.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserRepository userRepository;
	private final UserService userService;
	private final StripeService stripeService;
	private final ChangeEmailEventPublisher changeEmailEventPublisher;
	private final VerificationTokenService verificationTokenService;
	
	public UserController(UserRepository userRepository, UserService userService, StripeService stripeService, ChangeEmailEventPublisher changeEmailEventPublisher, VerificationTokenService verificationTokenService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.stripeService = stripeService;
		this.changeEmailEventPublisher = changeEmailEventPublisher;
		this.verificationTokenService = verificationTokenService;
	}
	
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, HttpServletRequest httpServletRequest, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		model.addAttribute("user", user);
		
		String sessionId = stripeService.createStripeSession(user, httpServletRequest);
		model.addAttribute("sessionId", sessionId);
		
		return "user/index";
	}
	
	@GetMapping("/edit")
	public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getEmail(), user.getBirthday(), user.getJob());
		
		model.addAttribute("userEditForm", userEditForm);
	
		return "user/edit";	
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		// メールアドレス変更＆登録済みのアドレスの場合、BindingResultオブジェクトにエラー内容を追加
		if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "user/edit";
		}
		
		// メールアドレス変更＆未登録のアドレスの場合、メール認証のイベントを発生させる
		if (userService.isEmailChanged(userEditForm) && !userService.isEmailRegistered(userEditForm.getEmail())) {
			//仮登録として、データーベース更新＆ユーザーを無効化する
			userService.update(userEditForm);
			User updatedUser = userRepository.getReferenceById(userEditForm.getId());
			userService.disableUser(updatedUser);
			
			String email = userEditForm.getEmail();
			String requestUrl = new String(httpServletRequest.getRequestURL());
			changeEmailEventPublisher.publishChangeEmailEvent(email, requestUrl);
			
			redirectAttributes.addFlashAttribute("successMessage", "変更いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員情報変更を完了してください。");
			return "redirect:/login";
			
		} else {
			userService.update(userEditForm);
			redirectAttributes.addFlashAttribute("successMessage", "会員情報を更新しました。");
			
			return "redirect:/user";
		}
	}
	
	@GetMapping("/update/verify")
	public String updateVerify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if (verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員情報を更新しました。";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}
		
		return "/auth/verify";
	}
	
	
	@GetMapping("/delete")
	public String delete() {
		return "user/delete";
	}
	
	@GetMapping("/deleted")
	public String deleted(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
		
		userService.delete(user);
		
		return "user/deleted";
	}
	
	// ロール情報を無料→有料に変更する
	@GetMapping("/upgraded")
	public String upgrade(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Integer userId = userDetailsImpl.getUser().getId();
		userService.upgradeRole(userId);
		
		return "redirect:/login?upgraded";
	}
	
	// ロール情報を有料→無料に変更する
	@GetMapping("/downgraded")
	public String downgrade(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		Integer userId = userDetailsImpl.getUser().getId();
		userService.downgradeRole(userId);
		
		return "redirect:/login?downgraded";
	}
	
}