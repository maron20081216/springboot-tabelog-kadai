package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.event.ReissueEventPublisher;
import com.example.nagoyameshi.event.SignupEventPublisher;
import com.example.nagoyameshi.form.ReissueConfirmForm;
import com.example.nagoyameshi.form.ReissueInputForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.repository.VerificationTokenRepository;
import com.example.nagoyameshi.service.UserService;
import com.example.nagoyameshi.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	private final VerificationTokenRepository verificationTokenRepository;
	private final UserService userService;
	private final SignupEventPublisher signupEventPublisher;
	private final ReissueEventPublisher reissueEventPublisher;
	private final VerificationTokenService verificationTokenService;
	
	public AuthController(VerificationTokenRepository verificationTokenRepository, UserService userService, SignupEventPublisher signupEventPublisher, ReissueEventPublisher reissueEventPublisher, VerificationTokenService verificationTokenService) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.userService = userService;
		this.signupEventPublisher = signupEventPublisher;
		this.reissueEventPublisher = reissueEventPublisher;
		this.verificationTokenService = verificationTokenService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		// メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
		if (userService.isEmailRegistered(signupForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		// パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
		if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "auth/signup";
		}
		
		User createdUser = userService.create(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");
		
		return "redirect:/login";
	}
	
	@GetMapping("/signup/verify")
	public String signupVerify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if (verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}
		
		return "auth/verify";
	}
	
	@GetMapping("/reissue")
	public String reissue(Model model) {
		model.addAttribute("reissueConfirmForm", new ReissueConfirmForm());
		return "auth/reissue-confirm";
	}
	
	@PostMapping("/reissue")
	public String reissue(@ModelAttribute @Validated ReissueConfirmForm reissueConfirmForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		// メールアドレスが登録されていなければ、BindingResultオブジェクトにエラー内容を追加する
		if (!userService.isEmailRegistered(reissueConfirmForm.getEmail())) {
		FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "入力いただいたメールアドレスは登録されていません。");
		bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "auth/reissue-confirm";
		}
		
		String email = reissueConfirmForm.getEmail();
		String requestUrl = new String(httpServletRequest.getRequestURL());
		reissueEventPublisher.publishReissueEvent(email, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "ご登録のメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、パスワード再発行を完了してください。");
		
		return "redirect:/login";
	}
	
	@GetMapping("/reissue/verify")
	public String reissueVerify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if (verificationToken == null) {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
			return "/auth/verify";
		}
		
		model.addAttribute("token", token);
		model.addAttribute("reissueInputForm", new ReissueInputForm());
		
		return "auth/reissue-input";
	}
	
	@PostMapping("/reissue/verify")
	public String reissuePassword(@RequestParam(name = "token") String token, @ModelAttribute @Validated ReissueInputForm reissueInputForm, BindingResult bindingResult, Model model) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		Integer userId = verificationToken.getUser().getId();

		// パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
		if (!userService.isSamePassword(reissueInputForm.getPassword(), reissueInputForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("token", token);
			return "auth/reissue-input";
		}
		
		userService.reissuePassword(userId, reissueInputForm);
		model.addAttribute("successMessage", "パスワード再発行が完了しました。");
		
		return "auth/verify";
	}

}