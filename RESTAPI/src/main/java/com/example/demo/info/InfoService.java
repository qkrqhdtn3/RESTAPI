package com.example.demo.info;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.info.model.City;
import com.example.demo.info.model.Project;
import com.example.demo.info.repository.CityRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InfoService {
	
	private final CityRepository cityRepository;
	
	public InfoService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	public Project getProjectInfo() {
		
		Project project = new Project();
		project.projectName = "preword";
		project.author = "hello-bryan";
		project.createdDate = new Date();
		
		return project;
	}
	
	public List<City> getCityList() {
		return this.cityRepository.findList();
	}
	public List<City> findCityByCodeAndPopulation(String countryCode, int population) {
		log.debug("countryCode = {}, population = {}", countryCode, population);
		return this.cityRepository.findByCountryCodeAndPopulation(countryCode, population);
	}
}
