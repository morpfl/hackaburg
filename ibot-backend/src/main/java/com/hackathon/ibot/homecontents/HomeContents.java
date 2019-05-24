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
	private InsurantType insurantType;
	
	@Getter @Setter
	private int living_pace_in_square_meters;
}
