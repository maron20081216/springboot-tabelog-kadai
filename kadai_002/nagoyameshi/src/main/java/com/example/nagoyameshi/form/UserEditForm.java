package com.example.nagoyameshi.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "氏名を入力してください。")
	private String name;
	
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスは正しい形式で入力してください。")
	private String email;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "生年月日を入力してください。")
	private LocalDate birthday;
	
	@NotBlank(message = "職業を選択してください。")
	private String job;
}
