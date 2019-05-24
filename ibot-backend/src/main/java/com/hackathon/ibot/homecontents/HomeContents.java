package com.hackathon.ibot.homecontents;

import com.hackathon.ibot.Insurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class HomeContents extends Insurance {
	@Getter @Setter
	private HomecontentsInsurantType homecontentsInsurantType;
	
	@Getter @Setter
	private int living_space_in_square_meters;
	
	public boolean hasNullField() {
		if(homecontentsInsurantType == null || living_space_in_square_meters == 0) return true;
		return false;
	}
}
