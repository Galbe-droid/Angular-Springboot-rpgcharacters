package com.gbertoldo.rpgcharacters.repository.ThreeTD;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDAcessory;

public interface ThreeTDAcessoryRepository extends MongoRepository<ThreeTDAcessory, String> {
	
	@Query("SELECT * FROM ThreeTDAcessory")
	List<ThreeTDAcessory> findAll();
	
	@Query("{'tags' : ?0}")
	List<ThreeTDAcessory> findByTags(String tag);
}

