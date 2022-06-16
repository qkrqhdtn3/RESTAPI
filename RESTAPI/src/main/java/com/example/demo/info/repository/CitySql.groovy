package com.example.demo.info.repository;

class CitySql{
	public static final String SELECT = """
		SELECT ID, Name, CountryCode, District, Population FROM city WHERE 1=1
	""";
	
	public static final String COUNTRY_CODE_CONDITION = """
		AND countryCode = :countryCode
	""";
	
	public static final String POPULATION_CONDITION = """
		AND population >= :population
	""";
}