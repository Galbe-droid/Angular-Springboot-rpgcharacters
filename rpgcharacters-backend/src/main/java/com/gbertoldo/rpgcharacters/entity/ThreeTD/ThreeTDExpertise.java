package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("ThreeTDExpertise")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDExpertise {
	//Base Info
	@Id
	private String id;
	String name; 
	String description;
	String[] tags;
}
