package com.devcamp.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcamp.api.model.Region;

@Repository
public interface IRegionRepository extends JpaRepository<Region, Long>{
	List<Region> findByCountryId(long countryId);
	Optional<Region> findByIdAndCountryId(long id, long instructorId);
}
