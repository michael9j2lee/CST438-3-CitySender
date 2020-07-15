package cst438_assignment2.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.ArgumentMatchers;

import cst438_assignment3.domain.*;
import cst438_assignment3.weather.*;


//class must be annotated as WebMvcTest,  not SpringBootTest
@SpringBootTest
public class CityServiceTest {
	
	// declare as @MockBean those classes which will be stubbed in the test
	// These classes must be Spring components (such as Repositories)
	// or @Service classes.

	@MockBean
	private CityService cityService;

	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private WeatherService weatherService;
	

 // This method is executed before each Test.
	@BeforeEach
	public void setUpEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	/*
	 * test for a correct answer
	 */

	@Test
	public void test1() throws Exception {

		Country country = new Country("TestCountryCode","EX");
		City city = new City(0,"TestCity","TestDistrict",0,country);
		TempAndTime t = new TempAndTime(0,1234,4321);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		given(weatherService.getTempAndTime("TestCity")).willReturn( t );
		
		// this is the stub for the CityRepository.  When given input parm of "TestCity", 
		// it will return a list of cities.
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		given(cityService.getCityInfo("TestCity")).willReturn( new CityInfo(city, t ));
		
		//check cityinfo
		CityInfo expectedResult = new CityInfo(
				new City( 0 ,"TestCity","TestDistrict", 0,
						new Country( "TestCountryCode","EX")),
				new TempAndTime(0,1234,4321));
		
		assertThat(expectedResult).isEqualTo( cityService.getCityInfo( "TestCity")) ;
		
		verify(cityService, times(1)).getCityInfo(ArgumentMatchers.any(String.class));
	}
	
	/*
	 * test for a incorrect answer
	 */
	@Test
	public void test2() throws Exception {
		Country country = new Country("TestCountryCode","EX");
		City city = new City(0,"TestCity","TestDistrict",0,country);
		TempAndTime t = new TempAndTime(0,1234,4321);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		CityInfo ct = new CityInfo(
				new City( 0 ,"FakeCity","FakeDistrict", 0,
						new Country( "FakeCountryCode","FA")),
				new TempAndTime(0,0,0)
						);

		given(weatherService.getTempAndTime("TestCity")).willReturn( t );
		given(cityService.getCityInfo("TestCity")).willReturn( new CityInfo(city, t ));
		
		// this is the stub for the CityRepository.  When given input parm of "TestCity", 
		// it will return a list of cities.
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		
		assertFalse( ct.equals(cityService.getCityInfo("TestCity")));
	}
	/*
	 * 
	 * Test for cities that is available in multiple cities.
	 */
	@Test
	public void test3() throws Exception {
		Country country = new Country("TestCountryCode","TestCountry");
		City city = new City(0,"TestCity","TestDistrict",0,country);
		City city2 = new City(1,"TestCity","TestDistrict2",1,country);
		TempAndTime t = new TempAndTime(0,1234,4321);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		cities.add(city2);
		
		CityInfo expectedResult = new CityInfo(
				new City( 0 ,"TestCity","TestDistrict", 0,
						new Country( "TestCountryCode","TestCountry")),
				new TempAndTime(0,1234,4321));

		given(weatherService.getTempAndTime("TestCity")).willReturn( t );
		given(cityService.getCityInfo("TestCity")).willReturn( new CityInfo(city, t ));
		
		// this is the stub for the CityRepository.  When given input parm of "TestCity", 
		// it will return a list of cities.
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		
		assertThat( expectedResult).isEqualTo(cityService.getCityInfo( "TestCity"));
	}
	
}