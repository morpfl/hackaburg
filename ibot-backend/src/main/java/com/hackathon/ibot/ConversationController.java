package com.hackathon.ibot;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversationController {
	
	@Autowired
	private ConversationService service;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/api/information")
	public ResponseEntity<ConversationResponseDTO> receiveNewInformation(@RequestBody ConversationRequestDTO requestDTO){
		ConversationResponseDTO response = this.service.persistInformation(requestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/api/start")
	public ResponseEntity<Integer> startNewConversation(){
		return ResponseEntity.status(HttpStatus.OK).body(this.service.openConversation());
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/api/recommendation/{id}/{type}")
	public ResponseEntity<List<FinalInsuranceResponseDTO>> recommendInsurance(@PathVariable("id") int id, @PathVariable("type") String type){
		List<FinalInsuranceResponseDTO> recommendationList = this.service.calculateRecs(id, type);
		return ResponseEntity.status(HttpStatus.OK).body(recommendationList);
	}

}
