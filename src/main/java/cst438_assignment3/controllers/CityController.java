package cst438_assignment3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cst438_assignment3.domain.*;
import cst438_assignment3.weather.*;

@Controller
public class CityController {
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	CityService cityService;
	
	@GetMapping(value="/city/{name}")
	public String getCity(@PathVariable("name") String name, Model model) {
		
		CityInfo cityInfo = cityService.getCityInfo(name);
		model.addAttribute(cityInfo);
		return "weather_show";
	}
	
	@PostMapping("/cities/reservation")
	public String createReservation(
			@RequestParam("city") String cityName, 
			@RequestParam("level") String level, 
			@RequestParam("email") String email, 
			Model model) {
		
		model.addAttribute("city", cityName);
		model.addAttribute("level", level);
		model.addAttribute("email", email);
		try {
			cityService.requestReservation(cityName, level, email);
			return "request_reservation";
		} catch (Exception e) {
			System.out.println("W3AController : createUser exception. "+e.getMessage());
			model.addAttribute("message", e.getMessage());
			return "error";
		}
	}

}
