package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent;

import com.example.nagoyameshi.entity.User;

import lombok.Getter;

@Getter
public class ReservationMailEvent extends ApplicationEvent {
	private User user;
	
	public ReservationMailEvent(Object source, User user) {
		super(source);
		
		this.user = user;
	}

}
