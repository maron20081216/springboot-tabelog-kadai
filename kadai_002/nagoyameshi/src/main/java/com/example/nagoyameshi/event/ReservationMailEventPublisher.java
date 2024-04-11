package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;

@Component
public class ReservationMailEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public ReservationMailEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishReservationMailEvent(User user) {
		applicationEventPublisher.publishEvent(new ReservationMailEvent(this, user));
	}
}
