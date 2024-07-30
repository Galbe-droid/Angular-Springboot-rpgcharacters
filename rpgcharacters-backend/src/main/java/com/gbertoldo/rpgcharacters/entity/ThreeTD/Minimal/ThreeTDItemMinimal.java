package com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal;

import com.gbertoldo.rpgcharacters.entity.general.Effects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreeTDItemMinimal {
	String name;
	String description;
	Effects[] effects;
}
