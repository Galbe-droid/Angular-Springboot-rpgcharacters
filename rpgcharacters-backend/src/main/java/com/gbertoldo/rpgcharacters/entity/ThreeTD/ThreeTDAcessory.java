package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gbertoldo.rpgcharacters.entity.general.AcessoryType;
import com.gbertoldo.rpgcharacters.entity.general.Effects;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("ThreeTDAcessory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDAcessory {
	//Base Info
	@Id
	private String id;
	@NotNull
	String name;
	@NotNull
	String description;
	String[] tags;
	
	//Acessory info
	@NotNull
	AcessoryType type;
	Effects[] effects;
}
