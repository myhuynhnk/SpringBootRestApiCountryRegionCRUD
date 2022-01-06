package com.devcamp.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.api.model.Country;
import com.devcamp.api.model.Region;
import com.devcamp.api.repository.ICountryRepository;
import com.devcamp.api.repository.IRegionRepository;

@RestController
public class RegionController {
	@Autowired 
	IRegionRepository regionRepository;
	
	@Autowired
	ICountryRepository countryRepository;
	
	@CrossOrigin
	@GetMapping("region/all")
	public ResponseEntity<List<Region>> getAllRegion() {
		try {
			List<Region> listRegion = new ArrayList<>();
			regionRepository.findAll().forEach(listRegion::add);;
			return new ResponseEntity<>(listRegion, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/region/detail/{id}")
	public ResponseEntity<Region> getRegionByRegionId(@PathVariable("id") long id) {
		try {
			Region region = regionRepository.findById(id).get();
			if(region != null) {
				return new ResponseEntity<>(region, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@GetMapping("/country/{countryId}/regions")
	public ResponseEntity<List<Region>> getRegionByCountryId(@PathVariable(value = "countryId") long countryId) {
		try {
			List<Region> listRegion = new ArrayList<>();
			Optional<Country> countryOptional = countryRepository.findById(countryId);
			if(countryOptional.isPresent()) {
				regionRepository.findByCountryId(countryId).forEach(listRegion::add);
				return new ResponseEntity<>(listRegion, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/country/{countryId}/regions/{id}")
    public Optional<Region> getRegionByRegionAndCountry(@PathVariable(value = "countryId") Long countryId,@PathVariable(value = "id") Long regionId) {
        return regionRepository.findByIdAndCountryId(regionId,countryId);
    }
	
	@CrossOrigin
	@PostMapping("/region/create/{countryId}")
	public ResponseEntity<Object> createRegion(@PathVariable(value = "countryId") long countryId, @RequestBody Region pRegion) {
		try {
			Optional<Country> countryOptional = countryRepository.findById(countryId);
			if(countryOptional.isPresent()) {
				Region newRegion = new Region();
				newRegion.setRegionCode(pRegion.getRegionCode());
				newRegion.setRegionName(pRegion.getRegionName());
				newRegion.setCountry(pRegion.getCountry());
				
				Country countryCurrent = countryOptional.get();
				newRegion.setCountry(countryCurrent);
				newRegion.setCountryName(countryCurrent.getCountryName());

				Region savedRegion = regionRepository.save(newRegion);
				return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("+++++++++++++++++++++::::: "+e.getCause().getCause().getMessage());
			return ResponseEntity.unprocessableEntity().body("Failed to Create specified country: "+e.getCause().getCause().getMessage());
		}
	}
	
	@CrossOrigin
	@PutMapping("/region/update/{id}")
	public ResponseEntity<Object> updateRegion(@PathVariable("id") long id, @RequestBody Region pRegion) {
		Optional<Region> regionOptional = regionRepository.findById(id);
		if(regionOptional.isPresent()) {
			Region region = regionOptional.get();
			
			region.setRegionCode(pRegion.getRegionCode());
			region.setRegionName(pRegion.getRegionName());
			Region savedRegion = regionRepository.save(region);
			try {
				return new ResponseEntity<>(savedRegion, HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("+++++++++++++++++:::::: " + e.getCause().getCause().getMessage());
				return ResponseEntity.unprocessableEntity().body("Failed to Create specified country: " + e.getCause().getCause().getMessage());
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/region/delete/{id}")
	public ResponseEntity<Region> deleteRegion(@PathVariable("id") long id) {
		try {
			Region region = regionRepository.findById(id).get();
			if(region != null) {
				regionRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
