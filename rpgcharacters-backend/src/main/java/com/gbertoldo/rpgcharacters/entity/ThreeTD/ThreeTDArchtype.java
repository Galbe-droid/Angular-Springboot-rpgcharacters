package com.gbertoldo.rpgcharacters.entity.ThreeTD;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("ThreeTDArchtype")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThreeTDArchtype {
	//Base Info
	@Id
	private String id;
	@NotNull
	@Size(min = 2, max = 20, message=("validation.archtype.name.out_of_bounds"))
	String name;
	@Size(max = 255, message=("validation.archtype.description.out_of_bounds"))
	String description; 
	String[] tags;
	@NotNull
	int cost;
	String[] traitsIds;
}
