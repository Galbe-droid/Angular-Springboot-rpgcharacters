package com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal;

import com.gbertoldo.rpgcharacters.entity.general.Effects;
import com.gbertoldo.rpgcharacters.entity.general.TechniqueTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreeTDTechniqueMinimal {
	String name;
	String description;
	TechniqueTypes type;
	Effects[] effects;
	String[] extraAction; 
	int mpCost;
	String range;
	String duration;
	String requirement;
}
