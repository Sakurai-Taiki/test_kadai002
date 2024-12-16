package com.example.kadai_002.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReserveRegisterForm {
	   private Integer houseId;
	    private String fromCheckinDate;
	    private String fromCheckinTime;
	    private Integer numberOfPeople;
}
