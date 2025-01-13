package com.example.kadai_002.form;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.entity.Users;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReserveEditForm {
	@NotNull
	private Integer id;
	
	@NotNull
	private Users users;
	
	@NotNull
	private Stores stores;
	
	@NotNull(message = "予約日を入力してください。")
	private LocalDate CheckinDate;
	
	@NotNull(message = "予約時間を入力してください。")
	private LocalTime CheckinTime;
	
	@NotNull(message = "予約人数を入力してください。")
	@Min(value = 1, message = "予約人数は1人以上に設定してください。")
	private Integer numberOfPeople;

}