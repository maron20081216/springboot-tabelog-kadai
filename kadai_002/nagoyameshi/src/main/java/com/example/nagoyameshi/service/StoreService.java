package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.StoreEditForm;
import com.example.nagoyameshi.form.StoreRegisterForm;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.StoreRepository;

@Service
public class StoreService {
	private final StoreRepository storeRepository;
	private final FavoriteRepository favoriteRepository;
	
	public StoreService(StoreRepository storeRepository, FavoriteRepository favoriteRepository) {
		this.storeRepository = storeRepository;
		this.favoriteRepository = favoriteRepository;
	}
	
	@Transactional
	public void create(StoreRegisterForm storeRegisterForm) {
		Store store = new Store();
		MultipartFile imageFile = storeRegisterForm.getImageFile();
		
		if(!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			store.setImageName(hashedImageName);
		}
		
		store.setCategory(storeRegisterForm.getCategory());
		store.setName(storeRegisterForm.getName());
		store.setDescription(storeRegisterForm.getDescription());
		store.setMinPrice(storeRegisterForm.getMinPrice());
		store.setMaxPrice(storeRegisterForm.getMaxPrice());
		store.setPostalCode(storeRegisterForm.getPostalCode());
		store.setAddress(storeRegisterForm.getAddress());
		store.setPhoneNumber(storeRegisterForm.getPhoneNumber());
		store.setOpenTime(storeRegisterForm.getOpenTime());
		store.setCloseTime(storeRegisterForm.getCloseTime());
		store.setHoliday(storeRegisterForm.getHoliday());
		store.setCapacity(storeRegisterForm.getCapacity());
		store.setSearchKeyword(storeRegisterForm.getSearchKeyword());
		store.setPriority(storeRegisterForm.getPriority());

		storeRepository.save(store);
	}
	
	@Transactional
	public void update(StoreEditForm storeEditForm) {
		Store store = storeRepository.getReferenceById(storeEditForm.getId());
		MultipartFile imageFile =  storeEditForm.getImageFile();
		
		if(!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			store.setImageName(hashedImageName);
		}
		
		store.setCategory(storeEditForm.getCategory());
		store.setName(storeEditForm.getName());
		store.setDescription(storeEditForm.getDescription());
		store.setMinPrice(storeEditForm.getMinPrice());
		store.setMaxPrice(storeEditForm.getMaxPrice());
		store.setPostalCode(storeEditForm.getPostalCode());
		store.setAddress(storeEditForm.getAddress());
		store.setPhoneNumber(storeEditForm.getPhoneNumber());
		store.setOpenTime(storeEditForm.getOpenTime());
		store.setCloseTime(storeEditForm.getCloseTime());
		store.setHoliday(storeEditForm.getHoliday());
		store.setCapacity(storeEditForm.getCapacity());
		store.setSearchKeyword(storeEditForm.getSearchKeyword());
		store.setPriority(storeEditForm.getPriority());

		storeRepository.save(store);
	}
	
	@Transactional
	public void favoriteOn(User userId, Store storeId) {
		Favorite favorite = new Favorite();
		
		favorite.setUser(userId);
		favorite.setStore(storeId);
		
		favoriteRepository.save(favorite);
	}
	
	@Transactional
	public void favoriteOff(Integer userId, Integer storeId) {
		favoriteRepository.deleteByUserIdAndStoreId(userId, storeId);
	}
	
		// UUIDを使って生成したファイル名を返す
		public String generateNewFileName(String fileName) {
			String[] fileNames = fileName.split("\\.");
			for (int i = 0; i < fileNames.length - 1; i++) {
				fileNames[i] = UUID.randomUUID().toString();
			}
			String hashedFileName = String.join(".", fileNames);
			return hashedFileName;
		}
		
		// 画像ファイルを指定したファイルにコピーする
		public void copyImageFile(MultipartFile imageFile, Path filePath) {
			try {
				Files.copy(imageFile.getInputStream(), filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
