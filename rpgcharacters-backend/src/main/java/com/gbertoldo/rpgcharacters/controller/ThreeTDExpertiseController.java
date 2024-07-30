package com.gbertoldo.rpgcharacters.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDExpertise;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDExpertiseMinimal;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDExpertiseRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/3D&TPericias")
public class ThreeTDExpertiseController {

	private final ThreeTDExpertiseRepository expertiseRepository;
	
	@Autowired
	public ThreeTDExpertiseController(ThreeTDExpertiseRepository expertiseRepository) {
		this.expertiseRepository = expertiseRepository;
	}
	
	@GetMapping("")
	List<ThreeTDExpertise> getAll(){
		return expertiseRepository.findAll();
	}
	
	@GetMapping("/minimal")
	List<ThreeTDExpertiseMinimal> getAllMinimal(){
		List<ThreeTDExpertise> all = getAll();
		List<ThreeTDExpertiseMinimal> allMinimal = new ArrayList<>();
		
		for(ThreeTDExpertise expertise: all) {
			allMinimal.add(Minimalize(expertise));
		}
		
		return allMinimal;		
	}
	
	@GetMapping("{id}")
	ThreeTDExpertise getById(@PathVariable String id) {
		Optional<ThreeTDExpertise> foundExpertise = expertiseRepository.findById(id);
		
		if(!foundExpertise.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDExpertise _expertise = foundExpertise.get();
		
		return _expertise;
	}
	
	@GetMapping("/minimal/{id}")
	ThreeTDExpertiseMinimal getByIdMinimal(@PathVariable String id) {
		return Minimalize(getById(id));
	}
	
	@GetMapping("/tags")
	List<ThreeTDExpertise> getByTags(@RequestBody String[] tags){
		List<ThreeTDExpertise> searchResult = new ArrayList<>();
		
		for(String tag : tags) {
			searchResult.addAll(expertiseRepository.findByTags(tag));
		}
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));

		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDExpertise expertise) {
		expertiseRepository.save(expertise);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDExpertise update(@PathVariable String id, @Valid @RequestBody ThreeTDExpertise updateExpertise) {
		Optional<ThreeTDExpertise> expertise = expertiseRepository.findById(id);
		
		if(!expertise.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDExpertise _expertise = expertise.get();
		_expertise.setDescription(updateExpertise.getDescription());
		_expertise.setName(updateExpertise.getName());
		_expertise.setTags(updateExpertise.getTags());
		
		expertiseRepository.save(_expertise);
		
		return _expertise;
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable String id) {
		expertiseRepository.deleteById(id);
	}
	
	//InnerMethods
	ThreeTDExpertiseMinimal Minimalize(ThreeTDExpertise expertise) {
		ThreeTDExpertiseMinimal _expertiseMinimal = new ThreeTDExpertiseMinimal();
		
		_expertiseMinimal.setDescription(expertise.getDescription());
		_expertiseMinimal.setName(expertise.getName());		
		
		return _expertiseMinimal;
	}
}
