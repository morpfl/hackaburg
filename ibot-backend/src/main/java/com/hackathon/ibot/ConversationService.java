package com.hackathon.ibot;

import java.util.List;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.ibot.bikeinsurance.BikeInsurance;
import com.hackathon.ibot.bikeinsurance.BikeInsuranceData;
import com.hackathon.ibot.bikeinsurance.BikeInsuranceRepository;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import javax.json.*;

@Service
public class ConversationService implements Comparator<FinalInsuranceResponseDTO>{
	
	@Autowired
	private ConversationRepository repository;
	@Autowired
	private InsuranceRepository insuranceRepository;
	@Autowired
	private BikeInsuranceRepository bikeInsuranceRepo;
	
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
		Conversation conversation = repository.findById(request.getId()).orElse(null);
		Insurance insurance = null;
		
		if(!alreadyStartedInsuranceCalculation(request.getType(), conversation)) {
			insurance = mapTypeToInsurance(conversation, request.getType());
			this.insuranceRepository.save(insurance);
			conversation.addInsurance(insurance);
			this.repository.save(conversation);
		}
		
		else {
			
			List<Insurance> insurances = conversation.getInsurances();
			for(Insurance singleInsurance : insurances) {
				if(singleInsurance.getType().name().equals(request.getType())) {
					insurance = singleInsurance;
					break;
				}
			}
		}	

		int index = conversation.getInsurances().indexOf(insurance);
			
		if(insurance instanceof BikeInsurance) mapBikeProperty(conversation, (BikeInsurance) insurance, request.getKey(), request.getValue());
		if(insurance instanceof CarInsurance) mapCarProperty(conversation, (CarInsurance) insurance, request.getKey(), request.getValue());
		if(insurance instanceof HomeContents) mapHomecontentsProperty(conversation, (HomeContents) insurance, request.getKey(), request.getValue());
		if(insurance instanceof HomeInsurance) mapHomeProperty(conversation, (HomeInsurance) insurance, request.getKey(), request.getValue());
		if(insurance instanceof LiabilityInsurance) mapLiabilityProperty(conversation, (LiabilityInsurance) insurance, request.getKey(), request.getValue());
			
		if(!hasNullField(request.getId(), insurance, index)) alreadyFinished = true;
		ConversationResponseDTO response = buildResponse(request.getId(), alreadyFinished, request.getType());
		return response;
	}

	private boolean alreadyStartedInsuranceCalculation(String type, Conversation conversation) {
		List<Insurance> allInsurances = conversation.getInsurances();
		for(Insurance insurance : allInsurances) {
			if(type.equals(insurance.getType().name())) return true;
		}
		return false;
	}

	private boolean hasNullField(int id, Insurance insurance, int index) {
		List<Insurance> insurances = this.repository.findById(id).orElse(null).getInsurances();
		if(insurances.get(index).hasNullField()) return true;
		return false;
	}

	private ConversationResponseDTO buildResponse(int id, boolean alreadyFinished, String type) {
		ConversationResponseDTO response = new ConversationResponseDTO(id, alreadyFinished, type);
		return response;
	}

	private void mapLiabilityProperty(Conversation conversation, LiabilityInsurance insurance, String property,
			String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "liabilitytype":
			insurance.setLiabilityType(LiabilityType.valueOf(value));
			break;
		case "money":
			insurance.setLiabilityInsuredAmount(LiabilityInsuredAmount.valueOf(value));
			break;
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
	}

	private void mapHomeProperty(Conversation conversation, HomeInsurance insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "housetype":
			insurance.setHouseType(HouseType.valueOf(value));
			break;
		case "money":
			insurance.setInsuredAmount(InsuredAmount.valueOf(value));
			break;
		case "number":
			insurance.setAge(Integer.parseInt(value));
			break;
		case "size":
			insurance.setSize(Integer.parseInt(value));
			break;
		}
		
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
	}

	private void mapHomecontentsProperty(Conversation conversation, HomeContents insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "liabilitytype":
			insurance.setHomecontentsInsurantType(HomecontentsInsurantType.valueOf(value));
			break;
		case "number":
			insurance.setLiving_space_in_square_meters(Integer.parseInt(value));
			break;
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
	}

	private void mapCarProperty(Conversation conversation, CarInsurance insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "cartype":
			insurance.setCarType(CarType.valueOf(value));
			break;
		case "areatype":
			insurance.setAreaType(AreaType.valueOf(value));
			break;
		case "kilometers_per_year":
			insurance.setKilometers_per_year(Integer.parseInt(value));
			break;
		}
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
		
	}

	private void mapBikeProperty(Conversation conversation, BikeInsurance insurance, String property, String value) {
		int index = conversation.getInsurances().indexOf(insurance);
		switch (property) {
		case "biketype":
			insurance.setBikeType(BikeType.valueOf(value));
			break;
		case "insuranttype":
			insurance.setBikeInsurantType(BikeInsurantType.valueOf(value));
			break;
		}
		this.insuranceRepository.save(insurance);
		conversation.getInsurances().set(index,insurance);
		this.repository.save(conversation);
	}

	private Insurance mapTypeToInsurance(Conversation conversation, String type) {
		switch(type) {
		case "Car":
			return new CarInsurance(conversation,InsuranceType.Car);
		case "Bike":
			return new BikeInsurance(conversation,InsuranceType.Bike);
		case "Homecontents":
			return new HomeContents(conversation,InsuranceType.Homecontents);
		case "Home":
			return new HomeInsurance(conversation,InsuranceType.Home);
		case "Liability":
			return new LiabilityInsurance(conversation,InsuranceType.Liability);
		default:
			return null;
		}
	}

	public int openConversation() {
		Conversation conversation = new Conversation();
		this.repository.save(conversation);
		return conversation.getId(); 
	}

	public List<FinalInsuranceResponseDTO> calculateRecs(int id, String type) {
		Conversation conversation = this.findConversationById(id);
		List<FinalInsuranceResponseDTO> finalInsurances = new LinkedList<FinalInsuranceResponseDTO>();
		Insurance possInsurance = this.insuranceRepository.findInsuranceByConversationAndType(conversation, InsuranceType.valueOf(type));
		switch(type) {
		case "Bike":
			BikeInsurance bikeInsurance = (BikeInsurance) possInsurance;
			List<BikeInsuranceData> bikeInsurances = this.bikeInsuranceRepo.findByBikeTypeAndInsurantType(bikeInsurance.getBikeType(),bikeInsurance.getBikeInsurantType());
			for(BikeInsuranceData data : bikeInsurances) {
				FinalInsuranceResponseDTO dto = new FinalInsuranceResponseDTO(data.getCompany(),bikeInsurance,data.getPremium_per_month());
				finalInsurances.add(dto);
			}
		}
		
		finalInsurances = prepareOutput(finalInsurances);
		
		return finalInsurances;
	}

	private List<FinalInsuranceResponseDTO> prepareOutput(List<FinalInsuranceResponseDTO> finalInsurances) {
		Collections.sort(finalInsurances, new Comparator<FinalInsuranceResponseDTO>() {
				@Override
				public int compare(FinalInsuranceResponseDTO insurance1, FinalInsuranceResponseDTO insurance2) {
					if(insurance1.getPremium() < insurance2.getPremium()) return -1;
					if(insurance1.getPremium() > insurance2.getPremium()) return 1;
					return 0;
				}
		});
		
		finalInsurances.remove(2);
		return finalInsurances;
	}

	private Conversation findConversationById(int id) {
		List<Conversation> allConversation = (List<Conversation>)this.repository.findAll();
		for(Conversation singleConversation : allConversation) {
			if(singleConversation.getId() == id) return singleConversation;
		}
		return null;
	}

	@Override
	public int compare(FinalInsuranceResponseDTO insurance1, FinalInsuranceResponseDTO insurance2) {
		if(insurance1.getPremium() < insurance2.getPremium()) return -1;
		if(insurance1.getPremium() > insurance2.getPremium()) return 1;
		return 0;
	}
	
}


