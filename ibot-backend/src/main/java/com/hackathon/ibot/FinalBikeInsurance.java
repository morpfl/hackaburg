package com.hackathon.ibot;

import com.hackathon.ibot.bikeinsurance.BikeInsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FinalBikeInsurance {
	
	@Getter @Setter
	private String name;
	@Getter @Setter
	private BikeInsurance insurance;
	@Getter @Setter
	private Float premium;
 
}
