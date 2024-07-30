package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDTechnique;

public interface ThreeTDTechniqueRepository extends MongoRepository<ThreeTDTechnique, String> {

	@Query("SELECT * FROM ThreeTDItem")
	List<ThreeTDTechnique> findAll();
	
	@Query("{'tags' : ?0}")
	List<ThreeTDTechnique> findByTags(String tag);
}
