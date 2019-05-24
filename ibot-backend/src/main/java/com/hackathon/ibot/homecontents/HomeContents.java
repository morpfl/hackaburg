package com.hackathon.ibot.homecontents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class HomeContents {
	@Getter @Setter
	private InsurantType insurantType;
	
	@Getter @Setter
	private int livingSpaceInSquareMeters;
}
