package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ReissueEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public ReissueEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishReissueEvent(String email, String requestUrl) {
		applicationEventPublisher.publishEvent(new ReissueEvent(this, email, requestUrl));
	}

}
