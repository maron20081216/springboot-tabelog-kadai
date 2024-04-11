package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ReissueEvent extends ApplicationEvent {
	private String email;
	private String requestUrl;
	
	public ReissueEvent(Object source, String email, String requestUrl) {
		super(source);
		
		this.email = email;
		this.requestUrl = requestUrl;
	}
	
}
