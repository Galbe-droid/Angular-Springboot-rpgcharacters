package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gbertoldo.rpgcharacters.entity.general.Effects;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("ThreeTDItem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDItem {
	//Base Info
	@Id
	private String id;
	@NotNull
	String name;
	String description;
	String[] tags;
	
	//Item Info
	@NotNull
	Effects[] effects;
}
