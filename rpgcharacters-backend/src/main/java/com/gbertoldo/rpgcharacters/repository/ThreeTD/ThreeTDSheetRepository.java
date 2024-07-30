package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDSheet;

public interface ThreeTDSheetRepository extends MongoRepository<ThreeTDSheet, String> {
	
	@Query("SELECT * FROM ThreeTDSheet WHERE characterName LIKE %?% VALUES ?")
	ThreeTDSheet findSheetByName(String name);
	
	@Query("SELECT * FROM ThreeTDSheet")
	List<ThreeTDSheet> findAll();
	
	@Query("{'tags' : ?0}")
	List<ThreeTDSheet> findByTags(String tags);
}
