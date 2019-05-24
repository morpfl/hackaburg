package com.hackathon.ibot.bikeinsurance;

import com.hackathon.ibot.Insurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class BikeInsurance extends Insurance {
	
	@Getter @Setter
	private BikeType bikeType;
	
	@Getter @Setter
	private PersonType personType;
}
