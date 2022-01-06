package com.devcamp.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "country_code", unique = true)
	private String countryCode;
	
	@Column(name = "country_name")
	private String countryName;
	
	@OneToMany(targetEntity = Region.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "country_id")
	private List<Region> regions;

	public Country() {
		super();
	}

	public Country(long id, String countryCode, String countryName, List<Region> regions) {
		super();
		this.id = id;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.regions = regions;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	
	
	
}
