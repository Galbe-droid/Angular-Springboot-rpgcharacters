package com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal;

import com.gbertoldo.rpgcharacters.entity.general.AcessoryType;
import com.gbertoldo.rpgcharacters.entity.general.Effects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreeTDAcessoryMinimal {
	//Base Info
		String name;
		String description;
		
		//Acessory info
		AcessoryType type;
		Effects[] effects;
}
