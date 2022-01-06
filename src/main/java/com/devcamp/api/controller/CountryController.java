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
import com.devcamp.api.repository.ICountryRepository;

@RestController
public class CountryController {
	@Autowired
	ICountryRepository countryRepository;
	
	@CrossOrigin
	@GetMapping("/country/all")
	public ResponseEntity<List<Country>> getAllCountry() {
		try {
			List<Country> listCountry = new ArrayList<>();
			countryRepository.findAll().forEach(listCountry::add);
			return new ResponseEntity<>(listCountry, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/country/detail/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable("id") long id) {
		try {
			Country country = countryRepository.findById(id).get();
			if(country != null) {
				return new ResponseEntity<>(country, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@PostMapping("/country/create")
	public ResponseEntity<Object> createCountry(@RequestBody Country pCountry) {
		try {
			Country newCountry = countryRepository.save(pCountry);
			return new ResponseEntity<>(newCountry, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("+++++++++++++++++:::::: " + e.getCause().getCause().getMessage());
			return ResponseEntity.unprocessableEntity().body("Failed to Create specified country: " + e.getCause().getCause().getMessage());
		}
	}
	
	@CrossOrigin
	@PutMapping("/country/update/{id}")
	public ResponseEntity<Object> updateCountry(@PathVariable("id") long id, @RequestBody Country pCountry) {
		Optional<Country> countryOptional = countryRepository.findById(id);
		if(countryOptional.isPresent()) {
			Country countryUpdate = countryOptional.get();
			countryUpdate.setCountryCode(pCountry.getCountryCode());
			countryUpdate.setCountryName(pCountry.getCountryName());
			countryUpdate.setRegions(pCountry.getRegions());
			Country savedCountryUpdate = countryRepository.save(countryUpdate);
			try {
				return new ResponseEntity<>(savedCountryUpdate, HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("+++++++++++++++++:::::: " + e.getCause().getCause().getMessage());
				return ResponseEntity.unprocessableEntity().body("Failed to Create specified country: " + e.getCause().getCause().getMessage());
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/country/delete/{id}")
	public ResponseEntity<Country> deleteCountryById(@PathVariable("id") long id) {
		try {
			Optional<Country> countryOptional = countryRepository.findById(id);
			if(countryOptional.isPresent()) {
				countryRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
