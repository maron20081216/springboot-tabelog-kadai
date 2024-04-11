package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	public Page<Store> findByNameLike(String keyword, Pageable pageable);
		
	public List<Store> findTop10ByOrderByPriority();
	
	
	// 店舗名・エリア・キーワードで検索（新着順）
	@Query(value = "SELECT * FROM stores WHERE name LIKE :nameKeyword OR address LIKE :addressKeyword OR search_keyword LIKE :searchKeyword ORDER BY created_at DESC, priority ASC", nativeQuery = true)
	public Page<Store> findKeywordNewOrder(@Param("nameKeyword") String nameKeyword, @Param("addressKeyword") String addressKeyword, @Param("searchKeyword") String searchKeyword, Pageable pageable);
	
	// 店舗名・エリア・キーワードで検索（料金が安い順）
	@Query(value = "SELECT * FROM stores WHERE name LIKE :nameKeyword OR address LIKE :addressKeyword OR search_keyword LIKE :searchKeyword ORDER BY max_price ASC, priority ASC", nativeQuery = true)	
	public Page<Store> findKeywordCheapOrder(@Param("nameKeyword") String nameKeyword, @Param("addressKeyword") String addressKeyword, @Param("searchKeyword") String searchKeyword, Pageable pageable); 
	
	// 店舗名・エリア・キーワードで検索（料金が高い順）
	@Query(value = "SELECT * FROM stores WHERE name LIKE :nameKeyword OR address LIKE :addressKeyword OR search_keyword LIKE :searchKeyword ORDER BY max_price DESC, priority ASC", nativeQuery = true)	
	public Page<Store> findKeywordExpensiveOrder(@Param("nameKeyword") String nameKeyword, @Param("addressKeyword") String addressKeyword, @Param("searchKeyword") String searchKeyword, Pageable pageable);
	
	
	// カテゴリ名で検索（新着順)
	@Query(value = "SELECT * FROM stores WHERE category_id = :categoryId ORDER BY created_at DESC, priority ASC", nativeQuery = true)
	public Page<Store> findCategoryNewOrder(@Param("categoryId") Integer categoryId, Pageable pageable);        
	
	// カテゴリ名で検索（料金が安い順)
	@Query(value = "SELECT * FROM stores WHERE category_id = :categoryId ORDER BY max_price ASC, priority ASC", nativeQuery = true)
	public Page<Store> findCategoryCheapOrder(@Param("categoryId") Integer categoryId, Pageable pageable);          
	
	// カテゴリ名で検索（料金が高い順)
	@Query(value = "SELECT * FROM stores WHERE category_id = :categoryId ORDER BY max_price DESC, priority ASC", nativeQuery = true)
	public Page<Store> findCategoryExpensiveOrder(@Param("categoryId") Integer categoryId, Pageable pageable);         
	
	
	// 価格で検索（新着順）
	@Query(value = "SELECT * FROM stores WHERE max_price <= :price ORDER BY created_at DESC, priority ASC", nativeQuery = true)
	public Page<Store> findMaxPriceNewOrder(@Param("price") Integer price, Pageable pageable); 
	
	// 価格で検索（料金が安い順）
	@Query(value = "SELECT * FROM stores WHERE max_price <= :price ORDER BY max_price ASC, priority ASC", nativeQuery = true)
	public Page<Store> findMaxPriceCheapOrder(@Param("price") Integer price, Pageable pageable);   
	
	// 価格で検索（料金が高い順）
	@Query(value = "SELECT * FROM stores WHERE max_price <= :price ORDER BY max_price DESC, priority ASC", nativeQuery = true)
	public Page<Store> findMaxPriceExpensiveOrder(@Param("price") Integer price, Pageable pageable);  
	
	
	// すべて検索（新着順）
	@Query(value = "SELECT * FROM stores ORDER BY created_at DESC, priority ASC", nativeQuery = true)
	public Page<Store> findAllNewOrder(Pageable pageable);  
	
	// すべて検索（料金が安い順）
	@Query(value = "SELECT * FROM stores ORDER BY max_price ASC, priority ASC", nativeQuery = true)
	public Page<Store> findAllCheapOrder(Pageable pageable);     
	
	// すべて検索（料金が高い順）
	@Query(value = "SELECT * FROM stores ORDER BY max_price DESC, priority ASC", nativeQuery = true)
	public Page<Store> findAllExpensiveOrder(Pageable pageable);

}