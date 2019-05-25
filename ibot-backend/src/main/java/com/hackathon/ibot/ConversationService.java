package com.hackathon.ibot;

import java.util.List;

import org.json.simple.parser.JSONParser;
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

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import javax.json.*;

@Service
public class ConversationService implements Comparator<FinalBikeInsurance>{
	
	private ConversationRepository repository;
	
	private InsuranceRepository insuranceRepository;
	
	public ConversationService(ConversationRepository repository, InsuranceRepository insuranceRepository) {
		this.repository = repository;
		this.insuranceRepository = insuranceRepository;
	}
	
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
			insurance = mapTypeToInsurance(request.getType());
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
		System.out.println(this.repository.findById(id).orElse(null));
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

	private Insurance mapTypeToInsurance(String type) {
		switch(type) {
		case "Car":
			System.out.println(type);
			return new CarInsurance(InsuranceType.Car);
		case "Bike":
			return new BikeInsurance(InsuranceType.Bike);
		case "Homecontents":
			return new HomeContents(InsuranceType.Homecontents);
		case "Home":
			return new HomeInsurance(InsuranceType.Home);
		case "Liability":
			return new LiabilityInsurance(InsuranceType.Liability);
		default:
			return null;
		}
	}

	/*
	public void testmethod() {
		Conversation conversation = new Conversation(1);
		this.repository.save(conversation);
		System.out.println(this.repository.findById(1).orElse(null));
		Insurance insurance = mapTypeToInsurance("Car");
		this.insuranceRepository.save(insurance);
	}
*/
	public int openConversation() {
		Conversation conversation = new Conversation();
		this.repository.save(conversation);
		return conversation.getId(); 
	}

	public List<FinalInsuranceResponseDTO> calculateRecs(int id, String type) {
		
		JSONParser parser = new JSONParser();
		List<FinalBikeInsurance> bestInsurances = new LinkedList<FinalBikeInsurance>();
		
		Insurance insurance = this.insuranceRepository.findInsuranceByIdAndType(id, InsuranceType.valueOf(type));
		if(insurance instanceof BikeInsurance) {
			BikeInsurance binsurance = (BikeInsurance) insurance;

			try {
				
				Object dreamService_bikeInsurance = parser.parse(new FileReader("/home/moritz/Downloads/TELIS_digital_insurance_track_data/bikeinsurance/dreamservice_bikeinsurance.json"));
				System.out.println("json object: " + dreamService_bikeInsurance);
				Object metropolica_bikeInsurance = parser.parse(new FileReader("metropolica_bikeinsurance.json"));
				Object utopia_bikeInsurance = parser.parse(new FileReader("utopia_bikeinsurance.json"));
				
				JsonObject jsonObject = (JsonObject) dreamService_bikeInsurance;
				JsonArray jsonArray= (JsonArray) jsonObject.getJsonArray("insurances");
            
				for(int i = 0; i < 8; i++) {
					String bikeType = jsonArray.getJsonObject(i).getString("bike type");
					System.out.println(bikeType);
					String insurantType = jsonArray.getJsonObject(i).getString("student");
					System.out.println(insurantType);
					float premium = Float.parseFloat(jsonArray.getJsonObject(i).getString("premium_per_month"));
					if ((binsurance.getBikeType().name() == bikeType)&&(binsurance.getBikeInsurantType().name() == insurantType)) {
						FinalBikeInsurance finalBikeInsurance = new FinalBikeInsurance("DreamService", binsurance, premium);
						bestInsurances.add(finalBikeInsurance);
					}
				}
				
				jsonObject = (JsonObject) metropolica_bikeInsurance;
				jsonArray = (JsonArray) jsonObject.getJsonArray("insurances");
				
				for(int i = 0; i < 8; i++) {
					String bikeType = jsonArray.getJsonObject(i).getString("bike type");
					String insurantType = jsonArray.getJsonObject(i).getString("student");
					float premium = Float.parseFloat(jsonArray.getJsonObject(i).getString("premium_per_month"));
					if ((binsurance.getBikeType().name() == bikeType)&&(binsurance.getBikeInsurantType().name() == insurantType)) {
						FinalBikeInsurance finalBikeInsurance = new FinalBikeInsurance("Metropolica", binsurance, premium);
						bestInsurances.add(finalBikeInsurance);
					}
				}
				
				jsonObject = (JsonObject) utopia_bikeInsurance;
				jsonArray = (JsonArray) jsonObject.getJsonArray("insurances");
				
				for(int i = 0; i < 8; i++) {
					String bikeType = jsonArray.getJsonObject(i).getString("bike type");
					System.out.println(bikeType);
					String insurantType = jsonArray.getJsonObject(i).getString("student");
					System.out.println(insurantType);
					float premium = Float.parseFloat(jsonArray.getJsonObject(i).getString("premium_per_month"));
					if ((binsurance.getBikeType().name() == bikeType)&&(binsurance.getBikeInsurantType().name() == insurantType)) {
						FinalBikeInsurance finalBikeInsurance = new FinalBikeInsurance("Utopia", binsurance, premium);
						bestInsurances.add(finalBikeInsurance);
					}
				}
			}
			catch(Exception e) {
				System.out.println("Error!!!!!!!!11");
			}
		}
		
		Collections.sort(bestInsurances, new Comparator<FinalBikeInsurance>() {
		    @Override
		    public int compare(FinalBikeInsurance first, FinalBikeInsurance second) {
		        return first.getPremium().compareTo(second.getPremium());
		    }
		});
			
		List<FinalInsuranceResponseDTO> finalInsurances = new LinkedList<FinalInsuranceResponseDTO>();
		
		//FinalInsuranceResponseDTO finalInsurance1 = new FinalInsuranceResponseDTO(bestInsurances.get(0).getName(),finalInsurances.get(0).getInsurance(),finalInsurances.get(0).getPremium());
		//FinalInsuranceResponseDTO finalInsurance2 = new FinalInsuranceResponseDTO(bestInsurances.get(1).getName(),finalInsurances.get(1).getInsurance(),finalInsurances.get(1).getPremium());
	
		//finalInsurances.add(finalInsurance1);
		//finalInsurances.add(finalInsurance2);
		
		return null;
	}

	@Override
	public int compare(FinalBikeInsurance o1, FinalBikeInsurance o2) {
		if(o1.getPremium() > o2.getPremium()) return 1;
		if(o1.getPremium() == o2.getPremium()) return 0;
		return -1;
	}

	
}


