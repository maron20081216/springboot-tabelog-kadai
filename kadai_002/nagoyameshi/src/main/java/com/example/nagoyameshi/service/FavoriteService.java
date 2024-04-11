package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.repository.FavoriteRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	
	// ユーザーがお気に入り登録したかどうかチェックする
	public boolean favoriteCheck(Integer userId, Integer storeId) {
		Boolean favoriteCheck = favoriteRepository.existsByUserIdAndStoreId(userId, storeId);
		return favoriteCheck;
	}

}
