package com.gbertoldo.rpgcharacters.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDExpertise;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDItem;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDSheet;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDTechnique;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.ThreeTDTrait;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDAcessoryMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDExpertiseMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDItemMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTechniqueMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Minimal.ThreeTDTraitMinimal;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Unpacked.ThreeTDArchtypeUnpacked;
import com.gbertoldo.rpgcharacters.entity.ThreeTD.Unpacked.ThreeTDSheetUnpacked;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDAcessoryRepository;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDExpertiseRepository;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDItemRepository;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDSheetRepository;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDTechniqueRepository;
import com.gbertoldo.rpgcharacters.repository.ThreeTD.ThreeTDTraitRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/3D&TFichas")
public class ThreeTDSheetController {
	
	private final ThreeTDSheetRepository sheetRepository;
	private final ThreeTDExpertiseController expertiseController;
	private final ThreeTDTechniqueController techniqueController;
	private final ThreeTDTraitController traitController;
	private final ThreeTDItemController itemController;
	private final ThreeTDAcessoryController acessoryController;
	
	private final ThreeTDArchtypeController archtypeController;
	
	@Autowired
	public ThreeTDSheetController(ThreeTDSheetRepository sheetRepository, ThreeTDExpertiseController expertiseController, ThreeTDTraitController traitController,
								  ThreeTDTechniqueController techniqueController, ThreeTDItemController itemController, 
								  ThreeTDAcessoryController acessoryController, ThreeTDArchtypeController archtypeController) {
		this.sheetRepository = sheetRepository;
		this.expertiseController = expertiseController;
		this.techniqueController = techniqueController;
		this.traitController = traitController;
		this.itemController = itemController;
		this.acessoryController = acessoryController;
		this.archtypeController = archtypeController;
	}
	
	@GetMapping("")
	List<ThreeTDSheet> getAll(){
		return sheetRepository.findAll();
	}
	
	@GetMapping("/unpacked")
	List<ThreeTDSheetUnpacked> getAllUnpacked(){
		List<ThreeTDSheet> all = sheetRepository.findAll();
		List<ThreeTDSheetUnpacked> allUnpacked = new ArrayList<>();
		
		for(ThreeTDSheet sheet : all) {
			allUnpacked.add(Unpacking(sheet));
		}
		
		return allUnpacked;
	}
	
	@GetMapping("/tags/{tags}")
	List<ThreeTDSheet> getByTags(@PathVariable String tags){
		List<ThreeTDSheet> searchResult = new ArrayList<>();

		String[] tagsSorted = tags.trim().split(",");
		
		for(String tag : tagsSorted) {
			searchResult.addAll(sheetRepository.findByTags(tag));
		}	
		
		Set<String> idsAlreadySeen = new HashSet<>();

		searchResult.removeIf(expertise -> !idsAlreadySeen.add(expertise.getId()));
		
		return searchResult;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	void create(@Valid @RequestBody ThreeTDSheet character){
		sheetRepository.save(character);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/update/{id}")
	ThreeTDSheet update (@PathVariable String id, @Valid @RequestBody ThreeTDSheet updateCharacter) {
		Optional<ThreeTDSheet> character = sheetRepository.findById(id);
		
		if(!character.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}		
		
		ThreeTDSheet _character = character.get();
		_character.setAbility(updateCharacter.getAbility());
		_character.setAcessories(updateCharacter.getAcessories());
		_character.setActionPoint(updateCharacter.getActionPoint());
		_character.setArchtype(updateCharacter.getArchtype());
		_character.setAuthor(updateCharacter.getAuthor());
		_character.setCharacterName(updateCharacter.getCharacterName());
		_character.setDungeonMaster(updateCharacter.getDungeonMaster());
		_character.setExpertises(updateCharacter.getExpertises());
		_character.setInventory(updateCharacter.getInventory());
		_character.setItens(updateCharacter.getItens());
		_character.setLifePoint(updateCharacter.getLifePoint());
		_character.setManaPoint(updateCharacter.getManaPoint());
		_character.setPower(updateCharacter.getPower());
		_character.setResistence(updateCharacter.getResistence());
		_character.setTags(updateCharacter.getTags());
		_character.setTechniques(updateCharacter.getTechniques());
		_character.setTraits(updateCharacter.getTraits());
		
		sheetRepository.save(_character);
		
		return _character;		
	}
	
	@DeleteMapping("/delete/{id}")
	void delete (@PathVariable String id) {
		sheetRepository.deleteById(id);
	}
	
	//Inner Methods
	ThreeTDSheetUnpacked Unpacking(ThreeTDSheet sheet) {
		ThreeTDSheetUnpacked sheetUnpacked = new ThreeTDSheetUnpacked();	
			
		sheetUnpacked.setAbility(sheet.getAbility());
		sheetUnpacked.setActionPoint(sheet.getActionPoint());
		sheetUnpacked.setAuthor(sheet.getAuthor());
		sheetUnpacked.setCharacterName(sheet.getCharacterName());
		sheetUnpacked.setDungeonMaster(sheet.getDungeonMaster());
		sheetUnpacked.setInventory(sheet.getInventory());
		sheetUnpacked.setLifePoint(sheet.getLifePoint());
		sheetUnpacked.setManaPoint(sheet.getManaPoint());
		sheetUnpacked.setPower(sheet.getPower());
		sheetUnpacked.setResistence(sheet.getResistence());
			
		if(sheet.getArchtype().length > 0) {
			ThreeTDArchtypeUnpacked[] archtypeFound = new ThreeTDArchtypeUnpacked[sheet.getArchtype().length];
			Integer count = 0;
			for(String ids : sheet.getArchtype()) {
				ThreeTDArchtypeUnpacked _archtype = archtypeController.getByIdUnpacked(ids);			
					
				archtypeFound[count] = _archtype;
				count++;
			}
			sheetUnpacked.setArchtype(archtypeFound);
		}
			
		if(sheet.getExpertises().length > 0) {
			ThreeTDExpertiseMinimal[] expertiseFound = new ThreeTDExpertiseMinimal[sheet.getExpertises().length];
			Integer count = 0;
			for(String ids : sheet.getExpertises()) {
				expertiseFound[count] = expertiseController.getByIdMinimal(ids);
				count++;
			}
			sheetUnpacked.setExpertises(expertiseFound);
		}
			
		if(sheet.getTechniques().length > 0) {
			ThreeTDTechniqueMinimal[] techniqueFound = new ThreeTDTechniqueMinimal[sheet.getTechniques().length];
			Integer count = 0;
			for(String ids : sheet.getTechniques()) {				
				techniqueFound[count] = techniqueController.getByIdMinimal(ids);
				count++;
			}
			sheetUnpacked.setTechniques(techniqueFound);
		}
		
		if(sheet.getTraits().length > 0) {
			ThreeTDTraitMinimal[] traitFound = new ThreeTDTraitMinimal[sheet.getTraits().length];
			Integer count = 0;
			for(String ids : sheet.getTraits()) {				
				traitFound[count] = traitController.getByIdMinimal(ids);
				count++;
			}
			sheetUnpacked.setTraits(traitFound);
		}
		
		if(sheet.getItens().length > 0) {
			ThreeTDItemMinimal[] itemFound = new ThreeTDItemMinimal[sheet.getItens().length];
			Integer count = 0;
			for(String ids : sheet.getItens()) {				
				itemFound[count] = itemController.getByIdMinimal(ids);
				count++;
			}
			sheetUnpacked.setItens(itemFound);
		}
		
		if(sheet.getAcessories().length > 0) {
			ThreeTDAcessoryMinimal[] acessoryFound = new ThreeTDAcessoryMinimal[sheet.getAcessories().length];	
			Integer count = 0;
			for(String ids : sheet.getAcessories()) {			
				acessoryFound[count] = acessoryController.getByIDMinimal(ids);
				count++;
			}
			sheetUnpacked.setAcessories(acessoryFound);
		}
			
					
		return sheetUnpacked;
	}	
}
