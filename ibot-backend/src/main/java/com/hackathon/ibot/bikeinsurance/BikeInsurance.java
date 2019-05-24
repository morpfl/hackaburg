package com.hackathon.ibot.bikeinsurance;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hackathon.ibot.Insurance;
import com.hackathon.ibot.InsuranceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BikeInsurance extends Insurance {
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private BikeType bikeType;
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private BikeInsurantType bikeInsurantType;
	
	public boolean hasNullField() {
		if(bikeType == null || bikeInsurantType == null) return true;
		return false;
	}

	public BikeInsurance(InsuranceType bike) {
		super(bike);
	}
}
