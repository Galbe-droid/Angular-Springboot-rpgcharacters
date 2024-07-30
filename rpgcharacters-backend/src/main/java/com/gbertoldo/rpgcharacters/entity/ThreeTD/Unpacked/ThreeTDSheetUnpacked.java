package com.gbertoldo.rpgcharacters.entity.ThreeTD.Unpacked;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDAcessoryMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDExpertiseMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDItemMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTechniqueMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTraitMinimal;
import com.gbertoldo.rpgcharacters.entity.general.InventorySize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDSheetUnpacked {
	String author;
	String dungeonMaster;
	
	//Character base info
	String characterName;
	ThreeTDArchtypeUnpacked[] archtype;
	InventorySize inventory;
	
	//CharacterStats
	int power;
	int actionPoint;
	int ability;
	int manaPoint;
	int resistence;
	int lifePoint;
	
	//CharacterSkills
	ThreeTDExpertiseMinimal[] expertises;
	ThreeTDTechniqueMinimal[] techniques;
	ThreeTDTraitMinimal[] traits;	
	
	//CharacterItens
	ThreeTDItemMinimal[] itens;
	ThreeTDAcessoryMinimal[] acessories;
}
