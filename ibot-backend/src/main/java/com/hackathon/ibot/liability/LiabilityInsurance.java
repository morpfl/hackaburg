package com.hackathon.ibot.liability;

import com.hackathon.ibot.Insurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class LiabilityInsurance extends Insurance {
	@Getter @Setter
	private LiabilityType liabilityType;
	
	@Getter @Setter
	private InsuredAmount insuredAmount;
}
