package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ChangeEmailEvent extends ApplicationEvent {
	private String email;
	private String requestUrl;
	
	public ChangeEmailEvent(Object source, String email, String requestUrl) {
		super(source);
		
		this.email = email;
		this.requestUrl = requestUrl;
	}
}
