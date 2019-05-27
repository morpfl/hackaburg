package com.hackathon.ibot;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
public class FinalInsuranceResponseDTO {
	
	@Getter @Setter
	private String company;
	
	@Getter @Setter
	private Insurance insurance;
	
	@Getter @Setter
	private Double premium;
	
}