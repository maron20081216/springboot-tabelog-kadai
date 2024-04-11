package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Template;

public interface TemplateRepository extends JpaRepository<Template, Integer> {
	public Template findByName(String name);

}
