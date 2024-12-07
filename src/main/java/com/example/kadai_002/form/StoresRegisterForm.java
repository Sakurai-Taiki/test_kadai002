package com.example.kadai_002.form;


import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoresRegisterForm {
	 @NotBlank(message = "店舗名を入力してください。")
     private String storeName;
         
     private MultipartFile imageFile;
     
     
     @NotBlank(message = "説明を入力してください。")
     private String description;   
     
     @NotNull(message = "価格帯（下限）を入力してください。")
     @Min(value = 1, message = "宿泊料金は1円以上に設定してください。")
     private Integer minBudget;  
     
     @NotNull(message = "価格帯（上限）を入力してください。")
     @Min(value = 1, message = "宿泊料金は1円以上に設定してください。")
     private Integer maxBudget;  
     
     @NotNull(message = "予約人数を入力してください。")
     @Min(value = 1, message = "人数は1人以上に設定してください。")
     private Integer seats;     
     
     @NotBlank(message = "郵便番号を入力してください。")
     private String storePostCode;
     
     @NotBlank(message = "開店時間を入力してください。")
     private String openHour;
     
     @NotBlank(message = "閉店時間を入力してください。")
     private String closeHour;
     
     @NotBlank(message = "定休日を入力してください。")
     private String closeDay;
     
     @NotBlank(message = "住所を入力してください。")
     private String storeAddress;
     
     @NotBlank(message = "電話番号を入力してください。")
     private String storePhoneNumber;
}
