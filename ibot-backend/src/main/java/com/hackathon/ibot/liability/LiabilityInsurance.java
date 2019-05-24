package com.hackathon.ibot.liability;

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
public class LiabilityInsurance extends Insurance {
	@Getter @Setter @Enumerated(EnumType.STRING)
	private LiabilityType liabilityType;
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private LiabilityInsuredAmount liabilityInsuredAmount;
	
	public boolean hasNullField() {
		if(liabilityType == null || liabilityInsuredAmount == null) return true;
		return false;
	}

	public LiabilityInsurance(InsuranceType liability) {
		super(liability);
	}
}
