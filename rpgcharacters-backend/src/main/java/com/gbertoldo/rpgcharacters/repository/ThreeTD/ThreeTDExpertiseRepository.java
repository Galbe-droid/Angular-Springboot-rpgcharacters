package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDExpertise;

public interface ThreeTDExpertiseRepository extends MongoRepository<ThreeTDExpertise, String>{
	
	@Query("SELECT * FROM ThreeTDExpertise")
	List<ThreeTDExpertise> findAll(); 
	
	@Query("{'tags' : '?0'}")
	List<ThreeTDExpertise> findByTags(String tags);
}
