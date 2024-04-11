package com.example.nagoyameshi.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.service.VerificationTokenService;

@Component
public class ReissueEventListener {
	private final UserRepository userRepository;
	private final VerificationTokenService verificationTokenService;
	private final JavaMailSender javaMailSender;
	
	public ReissueEventListener(UserRepository userRepository, VerificationTokenService verificationTokenService, JavaMailSender javaMailSender) {
		this.verificationTokenService = verificationTokenService;
		this.userRepository = userRepository;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	private void onReissueEvent(ReissueEvent reissueEvent) {
		User user = userRepository.findByEmail(reissueEvent.getEmail());
		String token = UUID.randomUUID().toString();
		verificationTokenService.update(user, token);
		
		String recipientAddress = user.getEmail();
		String subject = "メール認証";
		String confirmationUrl = reissueEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックして、パスワード再発行を完了してください。";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("marukiyo1126@gmail.com");
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}

}
