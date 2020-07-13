package cst438_assignment3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cst438_assignment3.domain.*;
import cst438_assignment3.weather.WeatherService;



@RestController
public class CityRestController {
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	CityService cityService;
	
	@GetMapping("/api/cities/{name}")
	public ResponseEntity<CityInfo> cityInfo(@PathVariable("name") String name ) {
		CityInfo cityInfo = cityService.getCityInfo(name);
		
		return new ResponseEntity<CityInfo>(cityInfo, HttpStatus.OK);
	}
	
}

