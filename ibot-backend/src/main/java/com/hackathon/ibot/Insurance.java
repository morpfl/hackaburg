package com.hackathon.ibot;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public abstract class Insurance {
	
	@Getter @Setter @Id @GeneratedValue
	private int id;
	
	@Getter @Setter @Enumerated(EnumType.STRING)
	private InsuranceType type;
	
	@Getter @Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Conversation_Id")
	@JsonIgnore
	private Conversation conversation;
	
	public abstract boolean hasNullField();
	
	public Insurance(Conversation conversation, InsuranceType type) {
		this.type = type;
		this.conversation = conversation; 
	}
}
