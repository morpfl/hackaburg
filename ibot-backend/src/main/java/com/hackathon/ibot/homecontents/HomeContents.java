package com.hackathon.ibot.homecontents;

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

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HomeContents extends Insurance {
	@Getter @Setter @Enumerated(EnumType.STRING)
	private HomecontentsInsurantType homecontentsInsurantType;
	
	@Getter @Setter
	private int living_space_in_square_meters;
	
	public boolean hasNullField() {
		if(homecontentsInsurantType == null || living_space_in_square_meters == 0) return true;
		return false;
	}

	public HomeContents(Conversation conversation, InsuranceType homecontents) {
		super(conversation, homecontents);
	}
}
