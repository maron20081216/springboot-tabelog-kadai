package com.example.nagoyameshi.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class SaleService {
	
	
	// ユーザーごとの売上金額を求める
	public Long calcurateSale(Integer roleId, Timestamp createdAt) {
		if (roleId == 1) {
			// 有料会員の場合売上金額を計算（会員登録日時(今回はこの日付を有料会員登録日とみなす)～本日で経過した月数*300円）
			LocalDateTime today = LocalDateTime.now();
			Long months = ChronoUnit.MONTHS.between(createdAt.toLocalDateTime(), today) + 1;
			Long sale = 300 * months;
			return sale;
		} else {
			// 有料会員以外の場合0を返す
			return (long)0;
		}
	}
	
	// 年齢層から、最短の誕生日の日付を求める
	public LocalDate calcurateShortestBirthday(Integer age) {
		Integer subtraction = age;
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		
		calendar.add(Calendar.YEAR, - subtraction);
		LocalDate shortestBirthday = new java.sql.Date(calendar.getTime().getTime()).toLocalDate(); // java.sql.Date型へ変換後、LocalDate型へ変換
		
		return shortestBirthday;
	}

	// 年齢層から、最遅の誕生日の日付を求める
	public LocalDate calcurateLatestBirthday(Integer age) {
		Integer subtraction = age + 10;
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		
		calendar.add(Calendar.YEAR, - subtraction);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		LocalDate latestBirthday = new java.sql.Date(calendar.getTime().getTime()).toLocalDate(); // java.sql.Date型へ変換後、LocalDate型へ変換
		
		return latestBirthday;
	}
}
