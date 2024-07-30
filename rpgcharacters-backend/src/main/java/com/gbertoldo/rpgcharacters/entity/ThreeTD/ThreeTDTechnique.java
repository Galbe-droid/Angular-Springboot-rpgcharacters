package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gbertoldo.rpgcharacters.entity.general.Effects;
import com.gbertoldo.rpgcharacters.entity.general.TechniqueTypes;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("ThreeTDTechnique")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDTechnique {
	//Base Info
	@Id
	private String id; 
	@NotNull
	String name;
	@NotNull
	String description;
	String[] tags;
	
	//Technique Info
	@NotNull
	TechniqueTypes type;
	Effects[] effects;
	String[] extraAction; 
	int mpCost;
	String range;
	String duration;
	String requirement;
}
