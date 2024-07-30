package com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal;

import com.gbertoldo.rpgcharacters.entity.general.TraitType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreeTDTraitMinimal {
	//Base Info
	String name;
	String description; 
	int cost;
	int level;
	boolean perLevel;
	TraitType type;	
}
