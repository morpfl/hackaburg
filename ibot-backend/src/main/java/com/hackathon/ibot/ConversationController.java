package com.hackathon.ibot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversationController {
	
	@Autowired
	private ConversationService service;
	
	@PostMapping("/api/information")
	public ResponseEntity<ConversationResponseDTO> receiveNewInformation(@RequestBody ConversationRequestDTO requestDTO){
		ConversationResponseDTO response = this.service.persistInformation(requestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/api/start")
	public ResponseEntity<Integer> startNewConversation(){
		return ResponseEntity.status(HttpStatus.OK).body(this.service.openConversation());
	}
	
	/*
	@PostMapping("/api/test")
	public void test() {
		this.service.testmethod();
	}
	*/

}
