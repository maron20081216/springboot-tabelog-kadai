package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	public boolean existsByStoreId(Integer storeId);
	public boolean existsByUserIdAndStoreId(Integer userId, Integer storeId);

	public void deleteByUserIdAndStoreId(Integer userId, Integer storeId);
	public void deleteByUserId(Integer userId);

	public Review findByUserIdAndStoreId(Integer userId, Integer storeId);

	public List<Review> findByStoreIdOrderByCreatedAtDesc(Integer storeId);
	public List<Review> findByStoreIdAndUserIdNotOrderByCreatedAtDesc(Integer storeId, Integer userId);
	
	@Query(value = "SELECT AVG(number_of_star) FROM reviews WHERE store_id = :storeId", nativeQuery = true)
	double avgStarsByStoreId(@Param("storeId") Integer storeId);
}
