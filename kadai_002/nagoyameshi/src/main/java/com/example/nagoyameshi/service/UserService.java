package com.example.nagoyameshi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReissueInputForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.repository.VerificationTokenRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final ReservationRepository reservationRepository;
	private final FavoriteRepository favoriteRepository;
	private final ReviewRepository reviewRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, ReservationRepository reservationRepository, FavoriteRepository favoriteRepository, ReviewRepository reviewRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.reservationRepository = reservationRepository;
		this.favoriteRepository = favoriteRepository;
		this.reviewRepository = reviewRepository;
		this.passwordEncoder = passwordEncoder;
		this.verificationTokenRepository = verificationTokenRepository;
	}
	
	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_FREEGENERAL");
		
		user.setRole(role);
		user.setName(signupForm.getName());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setBirthday(signupForm.getBirthday());
		user.setJob(signupForm.getJob());
		user.setEnabled(false);
		
		return userRepository.save(user);
	}
	
	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());
		
		user.setName(userEditForm.getName());
		user.setEmail(userEditForm.getEmail());
		user.setBirthday(userEditForm.getBirthday());
		user.setJob(userEditForm.getJob());
		
		userRepository.save(user);
	}
	
	@Transactional
	public void delete(User user) {
		Integer userId = user.getId();
		verificationTokenRepository.deleteByUserId(userId);
		reservationRepository.deleteByUserId(userId);
		favoriteRepository.deleteByUserId(userId);
		reviewRepository.deleteByUserId(userId);
		userRepository.deleteById(userId);
	}
	
	// パスワードを再発行する
	@Transactional
	public void reissuePassword(Integer userId, ReissueInputForm reissueInputForm) {
		User user = userRepository.getReferenceById(userId);
		
		user.setPassword(passwordEncoder.encode(reissueInputForm.getPassword()));
		
		userRepository.save(user);
	}
	
	// ロール情報を無料→有料に変更する
	@Transactional
	public void upgradeRole(Integer userId) {
		User user = userRepository.getReferenceById(userId);
		Role role = roleRepository.findByName("ROLE_PAIDGENERAL");
		
		user.setRole(role);
		
		userRepository.save(user);
	}
	
	// ロール情報を有料→無料に変更＆有料会員時の情報を削除
	@Transactional
	public void downgradeRole(Integer userId) {
		// ロール情報を有料→無料に変更
		User user = userRepository.getReferenceById(userId);
		Role role = roleRepository.findByName("ROLE_FREEGENERAL");
		
		user.setRole(role);
		
		userRepository.save(user);
		
		// 有料会員時の情報を削除(レビューは残す)
		reservationRepository.deleteByUserId(userId);
		favoriteRepository.deleteByUserId(userId);
	}
	
	
	// メールアドレスが登録済みかどうかをチェックする（登録済みならtrue、未登録ならfalse）
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
	// 氏名が登録済みかどうかをチェックする（登録済みならtrue、未登録ならfalse）
	public boolean isNameRegistered(String name) {
		User user = userRepository.findByName(name);
		return user != null;
	}
	
	// パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
	
	// ユーザーを有効にする
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	// ユーザーを無効にする
	@Transactional
	public void disableUser(User user) {
		user.setEnabled(false);
		userRepository.save(user);
	}
	
	// メールアドレスが変更されたかどうかをチェックする
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}
	
	
}