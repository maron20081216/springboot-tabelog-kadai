package com.example.nagoyameshi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.nagoyameshi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	public User findByName(String name);
	public User findByNameAndEmail(String name, String email);
	public Page<User> findByNameLike(String nameKeyword, Pageable pageable);
	
	
	public Page<User> findByRoleIdNotAndCreatedAtGreaterThanEqual(Integer adminRole, LocalDateTime periodFirst, Pageable pageable); // 期間で検索（開始日のみ、開始日～の期間=開始日より大きいものを検索）
	public Page<User> findByRoleIdNotAndCreatedAtLessThanEqual(Integer adminRole, LocalDateTime periodLast, Pageable pageable); // 期間で検索（終了日のみ、～終了日の期間=終了日より小さいものを検索）
	public Page<User> findByRoleIdNotAndCreatedAtBetween(Integer adminRole, LocalDateTime periodFirst, LocalDateTime periodLast, Pageable pageable); // 期間で検索（開始日＆終了日、開始日～終了日の期間を検索）
	
	public Page<User> findByRoleIdNotAndBirthdayGreaterThanEqual(Integer adminRole, LocalDate latestBirthday, Pageable pageable); // 年齢層で検索（10代以下）
	public Page<User> findByRoleIdNotAndBirthdayBetween(Integer adminRole, LocalDate latestBirthday, LocalDate shortestBirthday, Pageable pageable); // 年齢層で検索（20～80代）
	public Page<User> findByRoleIdNotAndBirthdayLessThanEqual(Integer adminRole, LocalDate shortestBirthday, Pageable pageable); // 年齢層で検索（90代以上）

	public Page<User> findByRoleIdNotAndJob(Integer adminRole, String job, Pageable pageable); // 職業で検索	
	public Page<User> findByRoleId(Integer roleId, Pageable pageable);        // ロールで検索

	public Page<User> findByRoleIdNot(Integer adminRole, Pageable pageable);   // すべて検索
	
	@Query(value = "SELECT id, name, birthday, TIMESTAMPDIFF(YEAR, `birthday`, CURDATE()) AS age FROM nagoyameshi_db.users", nativeQuery = true)
	public Page<User> findByRoleIdNotAndAgeLessThanEqual(Integer adminRole, Integer age, Pageable pageable); // 年齢で検索
	
	
	
	public List<User> findByRoleIdNotAndCreatedAtGreaterThanEqual(Integer adminRole, LocalDateTime periodFirst); // 期間で検索（開始日のみ、開始日～の期間=開始日より大きいものを検索）
	public List<User> findByRoleIdNotAndCreatedAtLessThanEqual(Integer adminRole, LocalDateTime periodLast); // 期間で検索（終了日のみ、～終了日の期間=終了日より小さいものを検索）
	public List<User> findByRoleIdNotAndCreatedAtBetween(Integer adminRole, LocalDateTime periodFirst, LocalDateTime periodLast); // 期間で検索（開始日＆終了日、開始日～終了日の期間を検索）

	public List<User> findByRoleIdNotAndBirthdayGreaterThanEqual(Integer adminRole, LocalDate shortestBirthday); // 年齢層で検索（10代以下）
	public List<User> findByRoleIdNotAndBirthdayBetween(Integer adminRole, LocalDate latestBirthday, LocalDate shortestBirthday); // 年齢層で検索（20～80代）
	public List<User> findByRoleIdNotAndBirthdayLessThanEqual(Integer adminRole, LocalDate latestBirthday); // 年齢層で検索（90代以上）

	public List<User> findByRoleIdNotAndJob(Integer adminRole, String job); // 職業で検索	
	public List<User> findByRoleId(Integer roleId);        // ロールで検索
	
	public List<User> findByRoleIdNot(Integer adminRole);   // すべて検索

	@Query(value = "SELECT id, name, birthday, TIMESTAMPDIFF(YEAR, `birthday`, CURDATE()) AS age FROM nagoyameshi_db.users", nativeQuery = true)
	public List<User> findByRoleIdNotAndAgeLessThanEqual(Integer adminRole, Integer age); // 年齢で検索
}