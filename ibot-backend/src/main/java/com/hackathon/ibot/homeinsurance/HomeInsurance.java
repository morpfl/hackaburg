package com.hackathon.ibot.homeinsurance;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hackathon.ibot.Conversation;
import com.hackathon.ibot.Insurance;
import com.hackathon.ibot.InsuranceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HomeInsurance extends Insurance {
	@Getter @Setter @Enumerated(EnumType.STRING)
	private HouseType houseType;
	@Getter @Setter
	private int age;
	@Getter @Setter
	private int size;
	@Getter @Setter @Enumerated(EnumType.STRING)
	private InsuredAmount insuredAmount;
	
	public boolean hasNullField() {
		if(houseType == null || age == 0 || size == 0 || insuredAmount == null) return true;
		return false;
	}

	public HomeInsurance(Conversation conversation, InsuranceType house) {
		super(conversation, house);
	}
}
