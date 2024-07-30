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

import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDItem;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDItemMinimal;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDItemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/3D&TItem")
public class ThreeTDItemController {
	
	private final ThreeTDItemRepository itemRepository;
	
	@Autowired
	public ThreeTDItemController(ThreeTDItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@GetMapping("")
	List<ThreeTDItem> getAll(){
		return itemRepository.findAll();
	}
	
	@GetMapping("/minimal")
	List<ThreeTDItemMinimal> getAllMinimal(){
		List<ThreeTDItem> all = getAll();
		List<ThreeTDItemMinimal> allMinimal = new ArrayList<>();
		
		for(ThreeTDItem item : all) {
			allMinimal.add(Minimalize(item));
		}
		
		return allMinimal;
	}
	
	@GetMapping("/{id}")
	ThreeTDItem getById(@PathVariable String id) {
		Optional<ThreeTDItem> foundItem =	itemRepository.findById(id);
		
		if(!foundItem.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDItem _item = foundItem.get();
		
		return _item;
	}
	
	@GetMapping("/minimal/{id}")
	ThreeTDItemMinimal getByIdMinimal(@PathVariable String id) {
		return Minimalize(getById(id));
	}
	
	@GetMapping("/tags")
	List<ThreeTDItem> getByTags(@RequestBody String[] tags){
		List<ThreeTDItem> searchResult = new ArrayList<>();
		
		for(String tag : tags) {
			searchResult.addAll(itemRepository.findByTags(tag));
		}
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));

		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDItem item) {
		itemRepository.save(item);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDItem update (@PathVariable String id, @Valid @RequestBody ThreeTDItem updateItem) {
		Optional<ThreeTDItem> item = itemRepository.findById(id);
		
		if(!item.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		ThreeTDItem _item = item.get();
		_item.setDescription(updateItem.getDescription());
		_item.setEffects(updateItem.getEffects());
		_item.setName(updateItem.getName());
		_item.setTags(updateItem.getTags());
		
		itemRepository.save(_item);
		
		return _item;
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable String id) {
		itemRepository.deleteById(id);
	}
	
	//InnerMethods
	ThreeTDItemMinimal Minimalize(ThreeTDItem item) {
		ThreeTDItemMinimal _minimalItem = new ThreeTDItemMinimal();
		
		_minimalItem.setDescription(item.getDescription());
		_minimalItem.setEffects(item.getEffects());
		_minimalItem.setName(item.getName());
		
		return _minimalItem;
	}
}
