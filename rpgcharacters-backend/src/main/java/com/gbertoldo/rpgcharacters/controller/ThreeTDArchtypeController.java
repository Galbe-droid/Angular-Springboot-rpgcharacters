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

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDArchtype;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTraitMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Unpacked.ThreeTDArchtypeUnpacked;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDArchtypeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/3D&TArquetipo")
public class ThreeTDArchtypeController {
	private final ThreeTDArchtypeRepository archtypeRepository;
	private final ThreeTDTraitController traitController;
	
	public ThreeTDArchtypeController(ThreeTDArchtypeRepository archtypeRepository, ThreeTDTraitController traitController) {
		this.archtypeRepository = archtypeRepository;
		this.traitController = traitController;
	}
	
	@GetMapping("")
	List<ThreeTDArchtype> getAll(){
		return archtypeRepository.findAll();
	}
	
	@GetMapping("/unpacked")
	List<ThreeTDArchtypeUnpacked> getAllUnpacked(){
		List<ThreeTDArchtype> all = getAll();
		List<ThreeTDArchtypeUnpacked> allUnpacked = new ArrayList<>();
		
		for(ThreeTDArchtype archtype : all) {
			allUnpacked.add(Unpacking(archtype));
		}
		
		return allUnpacked;		
	}
	
	@GetMapping("/{id}")
	ThreeTDArchtype getById(@PathVariable String id) {
		Optional<ThreeTDArchtype> foundArchtype = archtypeRepository.findById(id);
		
		if(!foundArchtype.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDArchtype _archtype = foundArchtype.get();
		
		return _archtype;
	}
	
	@GetMapping("/unpacked/{id}")
	ThreeTDArchtypeUnpacked getByIdUnpacked(@PathVariable String id) {		
		return Unpacking(getById(id));
	}
	
	
	@GetMapping("/tags")
	List<ThreeTDArchtype> getByTags(@RequestBody String[] tags){
		List<ThreeTDArchtype> searchResult = new ArrayList<>();
		
		for(String tag : tags) {
			searchResult.addAll(archtypeRepository.findByTags(tag));
		}
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));
		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDArchtype archtype){
		archtypeRepository.save(archtype);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDArchtype update(@PathVariable String id, @Valid @RequestBody ThreeTDArchtype updateArchtype) {
		Optional<ThreeTDArchtype> archtype = archtypeRepository.findById(id);
		
		if(!archtype.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDArchtype _archtype = archtype.get();
		_archtype.setCost(updateArchtype.getCost());
		_archtype.setDescription(updateArchtype.getDescription());
		_archtype.setName(updateArchtype.getName());
		_archtype.setTags(updateArchtype.getTags());
		_archtype.setTraitsIds(updateArchtype.getTraitsIds());
		
		archtypeRepository.save(_archtype);
		
		return _archtype;
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable String id) {
		archtypeRepository.deleteById(id);
	}
	
	//Inner Methods
	ThreeTDArchtypeUnpacked Unpacking(ThreeTDArchtype archtype) {
		Integer count = 0;
		Integer getTraitCount = archtype.getTraitsIds().length;
		ThreeTDArchtypeUnpacked archtypeUnpacked = new ThreeTDArchtypeUnpacked();
		
		archtypeUnpacked.setCost(archtype.getCost());
		archtypeUnpacked.setDescription(archtype.getDescription());
		archtypeUnpacked.setName(archtype.getName());
		if(getTraitCount > 0) {
			ThreeTDTraitMinimal[] traitsFound = new ThreeTDTraitMinimal[getTraitCount];
			for(String trait : archtype.getTraitsIds()) {
				ThreeTDTraitMinimal _traitMinimal = traitController.getByIdMinimal(trait);
			
				traitsFound[count] = _traitMinimal; 
				count++;
			}
			archtypeUnpacked.setTraitsMinimal(traitsFound);
		}		
		return archtypeUnpacked;
	}
}
