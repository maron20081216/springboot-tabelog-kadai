package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	public boolean existsByUserIdAndStoreId(Integer userId, Integer storeId);
	public void deleteByUserIdAndStoreId(Integer userId, Integer storeId);
	public void deleteByUserId(Integer userId);

	public Page<Favorite> findByUserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
}