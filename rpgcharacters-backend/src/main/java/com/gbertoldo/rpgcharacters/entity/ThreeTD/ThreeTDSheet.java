package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gbertoldo.rpgcharacters.entity.general.InventorySize;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "ThreeTDSheet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreeTDSheet {
	//Base Info
	@Id
	private String id;
	@NotNull
	String author;
	@Size(max = 20, message=("validation.sheet.dungeonMaster.out_of_bounds"))
	String dungeonMaster;
	@Size(max = 20, message=("validation.sheet.tags.out_of_bounds"))
	String[] tags;
	
	//Character base info
	@NotNull
	@Size(min = 2, max = 20, message=("validation.sheet.characterName.out_of_bounds"))
	String characterName;
	@Size(min = 1, max = 3, message=("validation.sheet.archtype.out_of_bounds"))
	String[] archtype;
	@NotNull
	InventorySize inventory;
	
	//CharacterStats
	@Positive
	int power;
	@Positive
	int actionPoint;
	@Positive
	int ability;
	@Positive
	int manaPoint;
	@Positive
	int resistence;
	@Positive
	int lifePoint;
	
	//CharacterSkills
	@Size(max = 10, message=("validation.sheet.expertise.out_of_bounds"))
	String[] expertises;
	@Size(max = 10, message=("validation.sheet.technique.out_of_bounds"))
	String[] techniques;
	@Size(max = 10, message=("validation.sheet.traits.out_of_bounds"))
	String[] traits;	
	
	//CharacterItens
	@Size(max = 10, message=("validation.sheet.item.out_of_bounds"))
	String[] itens;
	@Size(max = 10, message=("validation.sheet.acessory.out_of_bounds"))
	String[] acessories;
}
