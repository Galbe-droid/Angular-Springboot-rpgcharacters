package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDArchtype;

public interface ThreeTDArchtypeRepository extends MongoRepository<ThreeTDArchtype, String> {

	@Query("SELECT * FROM ThreeTDArchtype")
	List<ThreeTDArchtype> findAll();
	
	@Query("{'tags' : ?0}")
	List<ThreeTDArchtype> findByTags(String tag);
}
