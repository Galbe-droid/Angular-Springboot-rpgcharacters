package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gbertoldo.rpgcharacters.entity.general.TraitType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("ThreeTDTrait")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreeTDTrait {
	//Base Info
	@Id
	private String id;
	@NotNull
	String name;
	@NotNull
	String description; 
	String[] tags;
	int cost;
	int level;
	boolean perLevel;
	@NotNull
	TraitType type;
}
