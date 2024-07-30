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

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDTrait;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTraitMinimal;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDTraitRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/3D&TTraco")
public class ThreeTDTraitController {

	private final ThreeTDTraitRepository traitRepository;
	
	@Autowired
	public ThreeTDTraitController(ThreeTDTraitRepository traitRepository) {
		this.traitRepository = traitRepository;
	}
	
	@GetMapping("")
	List<ThreeTDTrait> getAll(){
		return traitRepository.findAll();
	}
	
	@GetMapping("/minimal")
	List<ThreeTDTraitMinimal> getAllMinimal(){
		List<ThreeTDTrait> all = getAll();
		List<ThreeTDTraitMinimal> allMinimal = new ArrayList<>();
		
		for(ThreeTDTrait _trait: all) {
			allMinimal.add(Minimalize(_trait));
		}
		
		return allMinimal;		
	}
	
	@GetMapping("{id}")
	ThreeTDTrait getById(@PathVariable String id) {
		Optional<ThreeTDTrait> foundTrait =	traitRepository.findById(id);
		
		if(!foundTrait.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDTrait _trait = foundTrait.get();
		
		return _trait;
	}
	
	@GetMapping("/minimal/{id}")
	ThreeTDTraitMinimal getByIdMinimal(@PathVariable String id) {
		return Minimalize(getById(id));
	}
	
	@GetMapping("/tags")
	List<ThreeTDTrait> getByTags(@RequestBody String[] tags){
		List<ThreeTDTrait> searchResult = new ArrayList<>();
		
		for(String tag : tags) {
			searchResult.addAll(traitRepository.findByTags(tag));
		}	
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));
		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDTrait trait) {
		traitRepository.save(trait);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDTrait update(@PathVariable String id, @Valid @RequestBody ThreeTDTrait updateTrait) {
		Optional<ThreeTDTrait> trait =	traitRepository.findById(id);
		
		if(!trait.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDTrait _trait = trait.get();
		_trait.setCost(updateTrait.getCost());
		_trait.setDescription(updateTrait.getDescription());
		_trait.setLevel(updateTrait.getLevel());
		_trait.setLevel(updateTrait.getLevel());
		_trait.setPerLevel(updateTrait.isPerLevel());
		_trait.setTags(updateTrait.getTags());
		_trait.setType(updateTrait.getType());
		
		traitRepository.save(_trait);
		
		return _trait;
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable String id) {
		traitRepository.deleteById(id);
	}
	
	//InnerMethod
	ThreeTDTraitMinimal Minimalize(ThreeTDTrait trait) {
		ThreeTDTraitMinimal _traitMinimal = new ThreeTDTraitMinimal();
		
		_traitMinimal.setCost(trait.getCost());
		_traitMinimal.setDescription(trait.getDescription());
		_traitMinimal.setLevel(trait.getLevel());
		_traitMinimal.setName(trait.getName());
		_traitMinimal.setPerLevel(trait.isPerLevel());
		_traitMinimal.setType(trait.getType());
		
		return _traitMinimal;
	}
	
	
}
