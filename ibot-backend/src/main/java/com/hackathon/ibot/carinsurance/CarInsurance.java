package com.hackathon.ibot.carinsurance;

import javax.persistence.Entity;

import com.hackathon.ibot.Insurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CarInsurance extends Insurance {
	
	@Getter @Setter
	private CarType carType;
	
	@Getter @Setter
	private AreaType areaType;  
	
	@Getter @Setter
	private int kilometers_per_year;
	
	public boolean hasNullField() {
		if(carType == null || areaType == null || kilometers_per_year == 0) return true;
		return false;
	}
}
