package com.example.nagoyameshi.form;

import java.time.LocalTime;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "店舗名を入力してください。")
	private String name;

	@NotNull(message = "カテゴリを選択してください。")
	private Category category;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "説明を入力してください。")
	private String description;
	
	@NotNull(message = "下限価格を入力してください。")
	@Min(value = 1, message = "価格は1円以上にしてください。")
	private Integer minPrice;
	
	@NotNull(message = "上限価格を入力してください。")
	@Min(value = 1, message = "価格は1円以上にしてください。")
	private Integer maxPrice;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	@DateTimeFormat(pattern = "HH:mm")
	@NotNull(message = "営業開始時間を入力してください。")
	private LocalTime openTime;

	@DateTimeFormat(pattern = "HH:mm")
	@NotNull(message = "営業終了時間を入力してください。")
	private LocalTime closeTime;
	
	@NotBlank(message = "定休日を入力してください。定休日がない場合は年中無休と入力ください。")
	private String holiday;
	
	@NotNull(message = "座席数を入力してください。")
	@Min(value = 1, message = "座席数は1以上にしてください。")
	private Integer capacity;
	
	@NotBlank(message = "検索キーワードを入力してください。")
	@Length(max = 50, message = "検索キーワードは50文字以内で入力してください。")
	private String searchKeyword;

	@NotNull(message = "優先順位を入力してください。")
	private Integer priority;
}
