package com.example.nagoyameshi.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "会社名を入力してください。")
	private String name;
	
	@NotBlank(message = "代表者を入力してください。")
	private String president;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "設立日を入力してください。")
	private LocalDate birthday;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "事業内容を入力してください。")
	private String business;
}
