package com.hackathon.ibot;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
	
	public abstract boolean hasNullField();
	
	public Insurance(InsuranceType type) {
		this.type = type;
	}
}
