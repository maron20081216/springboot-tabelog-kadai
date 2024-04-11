package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;

	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void create(User userId, Store storeId, ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		
		review.setUser(userId);
		review.setStore(storeId);
		review.setNumberOfStar(reviewRegisterForm.getNumberOfStar());
		review.setCommentTitle(reviewRegisterForm.getCommentTitle());
		review.setCommentContent(reviewRegisterForm.getCommentContent());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void update(ReviewEditForm reviewEditForm) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		
		review.setNumberOfStar(reviewEditForm.getNumberOfStar());
		review.setCommentTitle(reviewEditForm.getCommentTitle());
		review.setCommentContent(reviewEditForm.getCommentContent());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void delete(Integer userId, Integer storeId) {
		reviewRepository.deleteByUserIdAndStoreId(userId, storeId);
	}
	
	// レビュー星の平均値を求める
	public double starAverage(Integer storeId) {
		if (reviewRepository.existsByStoreId(storeId)) {
			//レビューある場合は平均値を返す
			return reviewRepository.avgStarsByStoreId(storeId);
		} else {
			//レビューない場合は0を返す
			return 0;
		}
	}
	
	// ユーザーがレビューしたかどうかチェックする
	public boolean reviewCheck(Integer userId, Integer storeId) {
		Boolean reviewCheck = reviewRepository.existsByUserIdAndStoreId(userId, storeId);
		return reviewCheck;
	}

}