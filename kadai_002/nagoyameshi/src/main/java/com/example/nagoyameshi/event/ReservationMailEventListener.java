package com.example.nagoyameshi.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.Template;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.TemplateRepository;

@Component
public class ReservationMailEventListener {
	private final TemplateRepository templateRepository;
	private final JavaMailSender javaMailSender ;
	
	public ReservationMailEventListener(TemplateRepository templateRepository, JavaMailSender javaMailSender) {
		this.templateRepository = templateRepository;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	private void onReservationMailEvent(ReservationMailEvent reservationMailEvent) {
		User user = reservationMailEvent.getUser();
		Template template = templateRepository.findByName("reservation");
		
		String userAddress = user.getEmail();
		String subject = template.getSubject();
		String message = template.getTemplate();
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("marukiyo1126@gmail.com");
		mailMessage.setTo(userAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		javaMailSender.send(mailMessage);
	}

}
