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

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDTechnique;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTechniqueMinimal;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDTechniqueRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/3D&TTecnica")
public class ThreeTDTechniqueController {

	private final ThreeTDTechniqueRepository techniqueRepository;
	
	@Autowired
	public ThreeTDTechniqueController(ThreeTDTechniqueRepository techniqueRepository) {
		this.techniqueRepository = techniqueRepository;
	}
	
	@GetMapping("")
	List<ThreeTDTechnique> getAll(){
		return techniqueRepository.findAll();
	}
	
	@GetMapping("/minimal")
	List<ThreeTDTechniqueMinimal> getAllMinimal(){
		List<ThreeTDTechnique> all = getAll();
		List<ThreeTDTechniqueMinimal> allMinimize = new ArrayList<>();
		
		for(ThreeTDTechnique technique: all) {
			allMinimize.add(Minimalize(technique));
		}
		
		return allMinimize;
	}
	
	@GetMapping("/{id}")
	ThreeTDTechnique getById(@PathVariable String id) {
		Optional<ThreeTDTechnique> foundTechnique = techniqueRepository.findById(id);
		
		if(!foundTechnique.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDTechnique _technique = foundTechnique.get();
		
		return _technique;		
	}
	
	@GetMapping("/minimal/{id}")
	ThreeTDTechniqueMinimal getByIdMinimal(@PathVariable String id) {
		return Minimalize(getById(id));
	}
	
	@GetMapping("/tags")
	List<ThreeTDTechnique> getByTags(@RequestBody String[] tags){
		List<ThreeTDTechnique> searchResult = new ArrayList<>();
		
		for(String tag : tags) {
			searchResult.addAll(techniqueRepository.findByTags(tag));
		}
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));
		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDTechnique technique) {
		techniqueRepository.save(technique);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDTechnique update(@PathVariable String id, @Valid @RequestBody ThreeTDTechnique updateTechnique) {
		Optional<ThreeTDTechnique> technique = techniqueRepository.findById(id);
		
		if(!technique.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDTechnique _technique = technique.get();
		_technique.setDescription(updateTechnique.getDescription());
		_technique.setDuration(updateTechnique.getDuration());
		_technique.setExtraAction(updateTechnique.getExtraAction());
		_technique.setMpCost(updateTechnique.getMpCost());
		_technique.setName(updateTechnique.getName());
		_technique.setRequirement(updateTechnique.getRequirement());
		_technique.setTags(updateTechnique.getTags());
		_technique.setType(updateTechnique.getType());
		
		techniqueRepository.save(_technique);
		
		return _technique;
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable String id) {
		techniqueRepository.deleteById(id);
	}
	
	//InnerMethods
	ThreeTDTechniqueMinimal Minimalize(ThreeTDTechnique technique) {
		ThreeTDTechniqueMinimal _minimalTechnique = new ThreeTDTechniqueMinimal();
		
		_minimalTechnique.setDescription(technique.getDescription());
		_minimalTechnique.setDuration(technique.getDuration());
		_minimalTechnique.setEffects(technique.getEffects());
		_minimalTechnique.setExtraAction(technique.getExtraAction());
		_minimalTechnique.setMpCost(technique.getMpCost());
		_minimalTechnique.setName(technique.getName());
		_minimalTechnique.setRange(technique.getRange());
		_minimalTechnique.setRequirement(technique.getRequirement());
		_minimalTechnique.setType(technique.getType());
		
		return _minimalTechnique;
	}
}
