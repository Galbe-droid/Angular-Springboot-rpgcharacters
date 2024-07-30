package com.gbertoldo.rpgcharacters.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDAcessory;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDAcessoryMinimal;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDAcessoryRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/3D&TAcessorio")
public class ThreeTDAcessoryController {
	
	private final ThreeTDAcessoryRepository acessoryRepository;
	
	public ThreeTDAcessoryController(ThreeTDAcessoryRepository acessoryRepository) {
		this.acessoryRepository = acessoryRepository;
	}
	
	@GetMapping("")
	List<ThreeTDAcessory> getAll(){
		return acessoryRepository.findAll();
	}
	
	@GetMapping("/minimal")
	List<ThreeTDAcessoryMinimal> getAllMinimal(){
		List<ThreeTDAcessory> all = acessoryRepository.findAll();
		List<ThreeTDAcessoryMinimal> allMinimal = new ArrayList<>();
		
		for(ThreeTDAcessory acessory: all) {
			allMinimal.add(Minimalize(acessory));
		}
		
		return allMinimal;
	} 
	
	@GetMapping("/{id}")
	ThreeTDAcessory getById(@PathVariable String id) {
		Optional<ThreeTDAcessory> foundAcessory = acessoryRepository.findById(id);
		
		if(!foundAcessory.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDAcessory _acessory = foundAcessory.get();
		
		return _acessory;		
	}
	
	@GetMapping("/minimal/{id}")
	ThreeTDAcessoryMinimal getByIDMinimal(@PathVariable String id) {
		ThreeTDAcessoryMinimal _minimalAcessory = Minimalize(getById(id));
		
		return _minimalAcessory;
	}
	
	@GetMapping("/tags")
	List<ThreeTDAcessory> getByTags(@RequestBody String[] tags){
		List<ThreeTDAcessory> searchResult = new ArrayList<>();
		
		for(String tag : tags) {
			searchResult.addAll(acessoryRepository.findByTags(tag));
		}
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));
		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDAcessory acessory) {
		acessoryRepository.save(acessory);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDAcessory update(@PathVariable String id, @Valid @RequestBody ThreeTDAcessory updateAcessory) {
		Optional<ThreeTDAcessory> acessory = acessoryRepository.findById(id);
		
		if(!acessory.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDAcessory _acessory = acessory.get();
		_acessory.setDescription(updateAcessory.getDescription());
		_acessory.setEffects(updateAcessory.getEffects());
		_acessory.setName(updateAcessory.getName());
		_acessory.setTags(updateAcessory.getTags());
		_acessory.setType(updateAcessory.getType());
		
		acessoryRepository.save(_acessory);
		
		return _acessory;
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable String id) {
		acessoryRepository.deleteById(id);
	}
	
	//InnerMethod
	ThreeTDAcessoryMinimal Minimalize(ThreeTDAcessory acessory) {
		ThreeTDAcessoryMinimal _minimalAcessory = new ThreeTDAcessoryMinimal();
		
		_minimalAcessory.setDescription(acessory.getDescription());
		_minimalAcessory.setEffects(acessory.getEffects());
		_minimalAcessory.setName(acessory.getName());
		_minimalAcessory.setType(acessory.getType());
		
		return _minimalAcessory;
	}
	
}
