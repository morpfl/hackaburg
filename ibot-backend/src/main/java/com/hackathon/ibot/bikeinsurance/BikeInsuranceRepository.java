package com.hackathon.ibot.bikeinsurance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BikeInsuranceRepository extends CrudRepository<BikeInsuranceData, Integer> {
	
	public List<BikeInsuranceData> findByBikeTypeAndInsurantType(BikeType bikeType, BikeInsurantType insurantType);
}
