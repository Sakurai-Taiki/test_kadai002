package com.example.kadai_002.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.form.StoresEditForm;
import com.example.kadai_002.form.StoresRegisterForm;
import com.example.kadai_002.repository.StoresRepository;

@Service
public class StoresService {
	private final StoresRepository StoresRepository;    
    
    public StoresService(StoresRepository StoresRepository) {
        this.StoresRepository = StoresRepository;        
    }    
    
    @Transactional
    public void create(StoresRegisterForm StoresRegisterForm) {
        Stores stores = new Stores();        
        MultipartFile imageFile = StoresRegisterForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedPhotoName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedPhotoName);
            copyImageFile(imageFile, filePath);
            stores.setPhotoName(hashedPhotoName);
        }
        
        stores.setStoreName(StoresRegisterForm.getStoreName());                
        stores.setDescription(StoresRegisterForm.getDescription());
        stores.setMinBudget(StoresRegisterForm.getMinBudget());
        stores.setMaxBudget(StoresRegisterForm.getMaxBudget());
        stores.setSeats(StoresRegisterForm.getSeats());
        stores.setStorePostCode(StoresRegisterForm.getStorePostCode());
        stores.setStoreAddress(StoresRegisterForm.getStoreAddress());
        stores.setStorePhoneNumber(StoresRegisterForm.getStorePhoneNumber());
        stores.setOpenHour(StoresRegisterForm.getOpenHour());
        stores.setCloseHour(StoresRegisterForm.getCloseHour());
        stores.setCloseDay(StoresRegisterForm.getCloseDay());
        
                    
        StoresRepository.save(stores);
    }  
    
    
    @Transactional
    public void update(StoresEditForm storesEditForm) {
    	Stores stores = StoresRepository.getReferenceById(storesEditForm.getId());
        MultipartFile imageFile = storesEditForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String PhotoName = imageFile.getOriginalFilename(); 
            String hashedPhotoName = generateNewFileName(PhotoName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedPhotoName);
            copyImageFile(imageFile, filePath);
            stores.setPhotoName(hashedPhotoName);
        }
        
        stores.setStoreName(storesEditForm.getStoreName());                
        stores.setDescription(storesEditForm.getDescription());
        stores.setMinBudget(storesEditForm.getMinBudget());
        stores.setMaxBudget(storesEditForm.getMaxBudget());
        stores.setSeats(storesEditForm.getSeats());
        stores.setStorePostCode(storesEditForm.getStorePostCode());
        stores.setStoreAddress(storesEditForm.getStoreAddress());
        stores.setStorePhoneNumber(storesEditForm.getStorePhoneNumber());
        stores.setOpenHour(storesEditForm.getOpenHour());
        stores.setCloseHour(storesEditForm.getCloseHour());
        stores.setCloseDay(storesEditForm.getCloseDay());
                    
        StoresRepository.save(stores);
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
