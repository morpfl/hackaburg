package com.hackathon.ibot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ConversationRequestDTO {
	
	@Getter @Setter
	private int id;
	
	@Getter @Setter
	private String type;
	
	@Getter @Setter
	private String key;
	
	@Getter @Setter
	private String value;
}
