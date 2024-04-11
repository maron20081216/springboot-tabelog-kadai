package com.example.nagoyameshi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
	private String priceId = "price_1OxTIzA31AgWmSsADU39aTLS";
	
	// セッションを作成し、Stripeに必要な情報を返す
	public String createStripeSession(User user, HttpServletRequest httpServletRequest) {
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		
		SessionCreateParams params =
			SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(
					SessionCreateParams.LineItem.builder()
						.setQuantity(1L)
						.setPrice(priceId)
						.build())
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.setSuccessUrl(requestUrl + "/upgraded")
				.setCancelUrl(requestUrl)
				.setSubscriptionData(
					SessionCreateParams.SubscriptionData.builder()
						.putMetadata("userId", user.getId().toString())
						.putMetadata("name", user.getName())
						.putMetadata("email", user.getEmail())
						.putMetadata("role", user.getRole().toString())
						.build())
			.build();
		
		try {
			Session session = Session.create(params);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace();
			return "";
		}
	}
	}
