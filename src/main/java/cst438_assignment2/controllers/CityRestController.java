package cst438_assignment2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cst438_assignment2.domain.City;
import cst438_assignment2.domain.CityRepository;
import cst438_assignment2.domain.CityWeather;
import cst438_assignment2.weather.WeatherService;

@RestController
public class CityRestController {
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	WeatherService weatherService;
	
//	@GetMapping("/cities/{name}")
//	public String 
	
	@GetMapping("/api/cities/{name}")
	public ResponseEntity<City> cityInfo(@PathVariable("name") String name ) {
		
		// look up city info from database.  Might be multiple cities with same name.
		List<City> cities = cityRepository.findByName(name);
		if ( cities.size()==0) {
			
			// city name not found.  Send 404 return code.
			return new ResponseEntity<City>( HttpStatus.NOT_FOUND);
			
		} else {
			
			// in case of multiple cities, take the first one.
		    City city=cities.get(0);
		    
		    // get current weather
			CityWeather cityWeather = weatherService.getWeather(name);
			// convert temp from degrees Kelvin to degrees Fahrenheit
			double tempF = Math.round((cityWeather.getTemp() - 273.15) * 9.0/5.0 + 32.0); 
			cityWeather.setTemp(tempF);
			city.setWeather(cityWeather);
			
			// return 200 status code (OK) and city information in JSON format
			return new ResponseEntity<City>(city, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/city/{name}")
	public ResponseEntity<City> deleteCity(@PathVariable("name") String name ) {
		
		// look up city info from database.  Might be multiple cities with same name.
		List<City> cities = cityRepository.findByName(name);
		if ( cities.size()==0) {
			// city name not found.  Send 404 return code.
			return new ResponseEntity<City>( HttpStatus.NOT_FOUND);
		} else {
			for (City c : cities) {
				cityRepository.delete(c);
			}
			// return 204,  request successful.  no content returned.
			return new ResponseEntity<City>( HttpStatus.NO_CONTENT);
		}
	}
	
}
