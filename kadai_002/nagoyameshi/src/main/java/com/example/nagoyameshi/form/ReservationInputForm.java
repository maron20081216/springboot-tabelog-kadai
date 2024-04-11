package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:ii")
	@NotBlank(message = "予約日と予約時間を入力してください。")
	private String reservationDateAndTime;
		
	@NotNull(message = "予約人数を入力してください。")
	@Min(value = 1, message = "予約人数は1人以上に設定してください。")
	private Integer numberOfPeople;
	
	// 予約日を取得する
	public LocalDate getReservationDate() {
		String[] ReservationDateAndTime = getReservationDateAndTime().split(" ");
		return LocalDate.parse(ReservationDateAndTime[0]);
	}
	
	// 予約時間を取得する
	public LocalTime getReservationTime() {
		String[] ReservationDateAndTime = getReservationDateAndTime().split(" ");
		return LocalTime.parse(ReservationDateAndTime[1]);
	}
}
