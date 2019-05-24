package com.hackathon.ibot;

import org.springframework.beans.factory.annotation.Autowired;
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
		this.service.extractInformation(requestDTO);
		
		/*TODO:
		 * falls id nicht existiert: id noch nicht vorhanden
	
		 * typ auslesen/auf Versicherung mappen und neue Versicherung zur Conversation hinzufügen
		 * RequestProperty auslesen und auf Property der Versicherung setzen (Setters)
		 * schauen ob noch null properties existieren 
		 * falls ja, is finished = true
		 * falls nein, is finished = false
		 */
	}
	

}
