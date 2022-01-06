package com.devcamp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcamp.api.model.Country;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long>{
	Country findByCountryCode(String countryCode);
}
