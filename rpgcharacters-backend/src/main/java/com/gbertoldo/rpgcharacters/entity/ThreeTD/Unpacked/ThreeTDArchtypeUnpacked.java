package com.gbertoldo.rpgcharacters.entity.ThreeTD.Unpacked;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTraitMinimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDArchtypeUnpacked {
	String name;
	String description; 
	int cost;
	ThreeTDTraitMinimal[] traitsMinimal;
}
