package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDItem;

public interface ThreeTDItemRepository extends MongoRepository<ThreeTDItem, String> {
	
	@Query("SELECT * FROM ThreeTDItem WHERE name LIKE %?% VALUES ?")
	ThreeTDItem findSheetByName(String name);
	
	@Query("SELECT * FROM ThreeTDItem")
	List<ThreeTDItem> findAll();
	
	@Query("{'tags' : ?0}")
	List<ThreeTDItem> findByTags(String tags);
}
