package com.hackathon.ibot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class test {
	public void testing() throws FileNotFoundException {
		 InputStream fis = new FileInputStream("dreamservice_bikeinsurance.json");
	     JsonReader reader = Json.createReader(fis);
	     JsonObject dreamService_bikeInsurance = reader.readObject();
	     reader.close();
	     
	     
	}
}
