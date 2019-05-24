package com.hackathon.ibot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponseDTO {
	@Getter @Setter
	private int id;
	
	@Getter @Setter
	private boolean isFinished;
	
	@Getter @Setter
	private String type;
	
}
