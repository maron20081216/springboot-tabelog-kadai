package com.example.nagoyameshi.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.service.SaleService;

@Controller
@RequestMapping("admin/sales")
public class AdminSaleControler {
	private final UserRepository userRepository;
	private final SaleService saleService;
	
	public AdminSaleControler(UserRepository userRepository, SaleService saleService) {
		this.userRepository = userRepository;
		this.saleService = saleService;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "periodFirst", required = false) LocalDateTime periodFirst,
						@RequestParam(name = "periodLast", required = false) LocalDateTime periodLast,
						@RequestParam(name = "age", required = false) Integer age,
						@RequestParam(name = "job", required = false) String job,
						@RequestParam(name = "role", required = false) Integer role,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model)
	{
		Page<User> userPage;
		
		if (periodFirst != null && periodLast == null) {
			userPage = userRepository.findByRoleIdNotAndCreatedAtGreaterThanEqual(3, periodFirst, pageable);
		} else if(periodFirst == null && periodLast != null) {
			userPage = userRepository.findByRoleIdNotAndCreatedAtLessThanEqual(3, periodLast, pageable);
		 } else if(periodFirst != null && periodLast != null) {
			userPage = userRepository.findByRoleIdNotAndCreatedAtBetween(3, periodFirst, periodLast, pageable);
		} else if (age != null) {
		 	if (age == 10) {
		 		LocalDate latestBirthday = saleService.calcurateLatestBirthday(age);
				userPage = userRepository.findByRoleIdNotAndBirthdayGreaterThanEqual(3, latestBirthday, pageable);
		 	} else if (age == 90) {
		 		LocalDate shortestBirthday = saleService.calcurateShortestBirthday(age);
				userPage = userRepository.findByRoleIdNotAndBirthdayLessThanEqual(3, shortestBirthday, pageable);
		 	} else {
		 		LocalDate latestBirthday = saleService.calcurateLatestBirthday(age);
		 		LocalDate shortestBirthday = saleService.calcurateShortestBirthday(age);
				userPage = userRepository.findByRoleIdNotAndBirthdayBetween(3, latestBirthday, shortestBirthday, pageable);	
		 	}
		} else if (job != null && !job.isEmpty()) {
			userPage = userRepository.findByRoleIdNotAndJob(3, job, pageable);
		} else if (role != null) {
			userPage = userRepository.findByRoleId(role, pageable);
		} else {
			userPage = userRepository.findByRoleIdNot(3, pageable);
		}
		
		
		List<User> userList;
		HashMap<Integer, Long> saleMap = new HashMap<Integer, Long>();
		
		if (periodFirst != null && periodLast == null) {
			userList = userRepository.findByRoleIdNotAndCreatedAtGreaterThanEqual(3, periodFirst);
		} else if(periodFirst == null && periodLast != null) {
			userList = userRepository.findByRoleIdNotAndCreatedAtLessThanEqual(3, periodLast);
		 } else if(periodFirst != null && periodLast != null) {
		 	userList = userRepository.findByRoleIdNotAndCreatedAtBetween(3, periodFirst, periodLast);
		} else if (age != null) {
		 	if (age == 10) {
		 		LocalDate latestBirthday = saleService.calcurateLatestBirthday(age);
				userList = userRepository.findByRoleIdNotAndBirthdayGreaterThanEqual(3, latestBirthday);
		 	} else if (age == 90) {
		 		LocalDate shortestBirthday = saleService.calcurateShortestBirthday(age);
				userList = userRepository.findByRoleIdNotAndBirthdayLessThanEqual(3, shortestBirthday);
		 	} else {
		 		LocalDate latestBirthday = saleService.calcurateLatestBirthday(age);
		 		LocalDate shortestBirthday = saleService.calcurateShortestBirthday(age);
				userList = userRepository.findByRoleIdNotAndBirthdayBetween(3, latestBirthday, shortestBirthday);
		 	}
		} else if (job != null && !job.isEmpty()) {
			userList = userRepository.findByRoleIdNotAndJob(3, job);
		} else if (role != null) {
			userList = userRepository.findByRoleId(role);
		} else {
			userList = userRepository.findByRoleIdNot(3);
		}
		
		for( int i = 0; i < userList.size(); i++) {
			Integer userId = userList.get(i).getId();

			Integer roleId = userList.get(i).getRole().getId();
			Timestamp createdAt = userList.get(i).getCreatedAt();
			Long sale = saleService.calcurateSale(roleId, createdAt);
			saleMap.put(userId, sale);
		}
		
		// 売上の合計金額を計算
		Long saleSum = (long)0;
		
		for( int i = 0; i < userList.size(); i++) {
			Integer userId = userList.get(i).getId();
			saleSum += saleMap.get(userId);
		}
		
		model.addAttribute("userPage", userPage);
		model.addAttribute("saleMap", saleMap);
		model.addAttribute("saleSum", saleSum);
		model.addAttribute("periodFirst", periodFirst);
		model.addAttribute("periodLast", periodLast);
		model.addAttribute("age", age);
		model.addAttribute("job", job);
		model.addAttribute("role", role);
		
		return "admin/sales/index";
		
	}

}
