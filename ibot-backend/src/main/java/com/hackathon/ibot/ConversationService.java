package com.hackathon.ibot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.ibot.bikeinsurance.BikeInsurance;
import com.hackathon.ibot.bikeinsurance.BikeInsurantType;
import com.hackathon.ibot.bikeinsurance.BikeType;
import com.hackathon.ibot.carinsurance.CarInsurance;
import com.hackathon.ibot.homecontents.HomeContents;
import com.hackathon.ibot.homeinsurance.HomeInsurance;
import com.hackathon.ibot.liability.LiabilityInsurance;

@Service
public class ConversationService {
	
	@Autowired
	private ConversationRepository repository;
	
	public boolean idExists(int id) {
		List<Conversation> allConversations = (List<Conversation>) this.repository.findAll();
		if(allConversations == null) return false;
		for(Conversation conversation : allConversations) {
			if(conversation.getId() == id) return true;
		}
		return false;
	}
	
	public void extractInformation(ConversationRequestDTO request) {
		if(!idExists(request.getId())) {		
			Conversation conversation = new Conversation(request.getId());
			Insurance insurance = mapTypeToInsurance(request.getType());
			conversation.addInsurance(insurance);
			
			if(insurance instanceof BikeInsurance) mapBikeProperty(conversation, (BikeInsurance) insurance, request.getKey(), request.getValue());
			/*
			if(insurance instanceof CarInsurance) lookupCarProperties();
			if(insurance instanceof HomeContents) lookupHomecontentsProperties();
			if(insurance instanceof HomeInsurance) lookupHomeProperties();
			if(insurance instanceof LiabilityInsurance) lookupLiabilityProperties();
			*/
		}
		
		/*TODO:
		 * falls id nicht existiert: conversation noch nicht vorhanden
		
		 * typ auslesen/auf Versicherung mappen und neue Versicherung zur Conversation hinzufügen
		 * RequestProperty auslesen und auf Property der Versicherung setzen (Setters)
		 * schauen ob noch null properties existieren 
		 * falls ja, is finished = true
		 * falls nein, is finished = false
		 */
		
	}

	private void mapBikeProperty(Conversation conversation, BikeInsurance insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "bikeType":
			insurance.setBikeType(BikeType.valueOf(value));
		case "insurantType":
			insurance.setBikeInsurantType(BikeInsurantType.valueOf(value));
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
	}

	private Insurance mapTypeToInsurance(String type) {
		switch(type) {
		case "Car":
			return new CarInsurance();
		case "Bike":
			return new BikeInsurance();
		case "Homecontents":
			return new HomeContents();
		case "Home":
			return new HomeInsurance();
		case "Lilability":
			return new LiabilityInsurance();
		default:
			return null;
		}
	}
}
