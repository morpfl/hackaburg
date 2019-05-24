package com.hackathon.ibot;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversationController {
	
	@PostMapping("/api/information")
	public ResponseEntity<ConversationResponseDTO> receiveNewInformation(@RequestBody )
	

}
