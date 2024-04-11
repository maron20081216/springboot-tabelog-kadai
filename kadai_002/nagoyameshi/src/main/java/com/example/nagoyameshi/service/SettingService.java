package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Profile;
import com.example.nagoyameshi.entity.Template;
import com.example.nagoyameshi.entity.Term;
import com.example.nagoyameshi.form.ProfileEditForm;
import com.example.nagoyameshi.form.TemplateEditForm;
import com.example.nagoyameshi.form.TermEditForm;
import com.example.nagoyameshi.repository.ProfileRepository;
import com.example.nagoyameshi.repository.TemplateRepository;
import com.example.nagoyameshi.repository.TermRepository;

@Service
public class SettingService {
	private final ProfileRepository profileRepository;
	private final TermRepository termRepository;
	private final TemplateRepository templateRepository;
	
	public SettingService(ProfileRepository profileRepository, TermRepository termRepository, TemplateRepository templateRepository) {
		this.profileRepository = profileRepository;
		this.termRepository = termRepository;
		this.templateRepository = templateRepository;
	}
	
	@Transactional
	public void profileUpdate(ProfileEditForm profileEditForm) {
		Profile profile = profileRepository.getReferenceById(profileEditForm.getId());
		
		profile.setName(profileEditForm.getName());
		profile.setPresident(profileEditForm.getPresident());
		profile.setBirthday(profileEditForm.getBirthday());
		profile.setPostalCode(profileEditForm.getPostalCode());
		profile.setAddress(profileEditForm.getAddress());
		profile.setBusiness(profileEditForm.getBusiness());
		
		profileRepository.save(profile);
	}
	
	@Transactional
	public void termUpadate(TermEditForm termEditForm) {
		Term term = termRepository.getReferenceById(termEditForm.getId());
		term.setContent(termEditForm.getContent());
		
		termRepository.save(term);
	}
	
	@Transactional
	public void templateUpdate(TemplateEditForm templateEditForm) {
		Template template = templateRepository.getReferenceById(templateEditForm.getId());
		
		template.setName(templateEditForm.getName());
		template.setTemplate(templateEditForm.getTemplate());
		
		templateRepository.save(template);
	}
}
