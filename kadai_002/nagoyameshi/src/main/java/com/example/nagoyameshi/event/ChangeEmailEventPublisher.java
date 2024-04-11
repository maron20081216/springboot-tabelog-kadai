package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ChangeEmailEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public ChangeEmailEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishChangeEmailEvent(String email, String requestUrl) {
		applicationEventPublisher.publishEvent(new ChangeEmailEvent(this, email, requestUrl));
		
	}

}
