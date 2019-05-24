package com.hackathon.ibot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Conversation {

	@Getter @Setter @GeneratedValue
	private int id;
	@Getter @Setter 
	private Insurance intent;
}
