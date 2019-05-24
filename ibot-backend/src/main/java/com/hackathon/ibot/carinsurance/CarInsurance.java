package com.hackathon.ibot.carinsurance;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.hackathon.ibot.Insurance;
import com.hackathon.ibot.InsuranceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CarInsurance extends Insurance {
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private CarType carType;
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private AreaType areaType;  
	
	@Getter @Setter
	private int kilometers_per_year;
	
	public boolean hasNullField() {
		if(carType == null || areaType == null || kilometers_per_year == 0) return true;
		return false;
	}

	public CarInsurance(InsuranceType type) {
		super(type);
	}
}
