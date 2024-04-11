package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	
	public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, StoreRepository storeRepository) {
		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
		this.reservationRepository = reservationRepository;
	}
	
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Reservation reservation = new Reservation();
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		Store store = storeRepository.getReferenceById(reservationRegisterForm.getStoreId());
		
		reservation.setUser(user);
		reservation.setStore(store);
		reservation.setReservationDate(reservationRegisterForm.getReservationDate());
		reservation.setReservationTime(reservationRegisterForm.getReservationTime());
		reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
		
		reservationRepository.save(reservation);
	}
	
	@Transactional
	public void cancel(Integer userId, Integer storeId) {
		reservationRepository.deleteByUserIdAndStoreId(userId, storeId);
	}
	
	
	// 予約人数が定員以下かをチェックする
	public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
		return numberOfPeople <= capacity;
	}
}
