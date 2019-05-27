package com.hackathon.ibot.bikeinsurance;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BikeInsuranceData {
	@Getter @Setter
	@GeneratedValue @Id 
	private int id;
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private BikeType bikeType;
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private BikeInsurantType insurantType;
	
	@Getter @Setter
	private double premium_per_month;
	
	@Getter @Setter
	private String company;
}
