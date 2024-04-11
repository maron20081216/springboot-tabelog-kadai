package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "名称を入力してください。")
	private String name;
	
	@NotBlank(message = "件名を入力してください。")
	private String subject;
	
	@NotBlank(message = "内容を入力してください。")
	private String template;
}
