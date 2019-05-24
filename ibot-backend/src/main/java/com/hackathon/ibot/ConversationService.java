package com.hackathon.ibot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.ibot.bikeinsurance.BikeInsurance;
import com.hackathon.ibot.bikeinsurance.BikeInsurantType;
import com.hackathon.ibot.bikeinsurance.BikeType;
import com.hackathon.ibot.carinsurance.AreaType;
import com.hackathon.ibot.carinsurance.CarInsurance;
import com.hackathon.ibot.carinsurance.CarType;
import com.hackathon.ibot.homecontents.HomeContents;
import com.hackathon.ibot.homecontents.HomecontentsInsurantType;
import com.hackathon.ibot.homeinsurance.HomeInsurance;
import com.hackathon.ibot.homeinsurance.HouseType;
import com.hackathon.ibot.homeinsurance.InsuredAmount;
import com.hackathon.ibot.liability.LiabilityInsurance;
import com.hackathon.ibot.liability.LiabilityType;
import com.hackathon.ibot.liability.LiabilityInsuredAmount;

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
	
	public ConversationResponseDTO persistInformation(ConversationRequestDTO request) {
		boolean alreadyFinished = false;
		Insurance insurance = mapTypeToInsurance(request.getType());
		
		if(!idExists(request.getId())) {		
			Conversation conversation = new Conversation(request.getId());
			conversation.addInsurance(insurance);
			
			if(insurance instanceof BikeInsurance) mapBikeProperty(conversation, (BikeInsurance) insurance, request.getKey(), request.getValue());
			if(insurance instanceof CarInsurance) mapCarProperty(conversation, (CarInsurance) insurance, request.getKey(), request.getValue());
			if(insurance instanceof HomeContents) mapHomecontentsProperty(conversation, (HomeContents) insurance, request.getKey(), request.getValue());
			if(insurance instanceof HomeInsurance) mapHomeProperty(conversation, (HomeInsurance) insurance, request.getKey(), request.getValue());
			if(insurance instanceof LiabilityInsurance) mapLiabilityProperty(conversation, (LiabilityInsurance) insurance, request.getKey(), request.getValue());
			
		}
		
		if(!insurance.hasNullField()) alreadyFinished = true;
		
		ConversationResponseDTO response = buildResponse(request.getId(), alreadyFinished, request.getType());
		return response;
	}

	private ConversationResponseDTO buildResponse(int id, boolean alreadyFinished, String type) {
		ConversationResponseDTO response = new ConversationResponseDTO(id, alreadyFinished, type);
		return response;
	}

	private void mapLiabilityProperty(Conversation conversation, LiabilityInsurance insurance, String property,
			String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "liabilityType":
			insurance.setLiabilityType(LiabilityType.valueOf(value));
		case "insuredAmount":
			insurance.setLiabilityInsuredAmount(LiabilityInsuredAmount.valueOf(value));
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
		
	}

	private void mapHomeProperty(Conversation conversation, HomeInsurance insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "houseType":
			insurance.setHouseType(HouseType.valueOf(value));
		case "insuredAmount":
			insurance.setInsuredAmount(InsuredAmount.valueOf(value));
		case "age":
			insurance.setAge(Integer.parseInt(value));
		case "size":
			insurance.setSize(Integer.parseInt(value));
		}
		
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
	}

	private void mapHomecontentsProperty(Conversation conversation, HomeContents insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "homecontentsInsurantType":
			insurance.setHomecontentsInsurantType(HomecontentsInsurantType.valueOf(value));
		case "living_space_in_square_meters":
			insurance.setLiving_space_in_square_meters(Integer.parseInt(value));
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
	}

	private void mapCarProperty(Conversation conversation, CarInsurance insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "carType":
			insurance.setCarType(CarType.valueOf(value));
		case "areaType":
			insurance.setAreaType(AreaType.valueOf(value));
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
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
