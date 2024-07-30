package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDTrait;

public interface ThreeTDTraitRepository extends MongoRepository<ThreeTDTrait, String> {

	@Query("SELECT * FROM ThreeTDTrait")
	List<ThreeTDTrait> findAll();
	
	@Query("{'tags' : ?0}")
	List<ThreeTDTrait> findByTags(String tags);
}
