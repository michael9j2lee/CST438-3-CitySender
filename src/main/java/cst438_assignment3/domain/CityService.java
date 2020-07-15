package cst438_assignment3.domain;

import java.util.List;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438_assignment3.domain.*;
import cst438_assignment3.weather.*;

@Service
public class CityService {
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private WeatherService weatherService;
	
    @Autowired
    private RabbitTemplate rabbitTemplate;
	
    @Autowired
    private FanoutExchange fanout;

	
	public CityInfo getCityInfo(String cityName) {

		List<City> cities = cityRepository.findByName(cityName);
		CityInfo cityInfo = null;
		if ( cities.size()>0) {
			City city = cities.get(0);
			TempAndTime tempAndTime = weatherService.getTempAndTime(cityName);
			cityInfo = new CityInfo(city, tempAndTime);
		} else {
			cityInfo = new CityInfo(0,"No Results Found for " + cityName,"","","",0,0,"");
		}
		return cityInfo;
     }
	
    public void requestReservation( 
            String cityName, 
            String level, 
            String email) {
	String msg  = "{\"cityName\": \""+ cityName + 
        "\" \"level\": \""+level+
        "\" \"email\": \""+email+"\"}" ;
	System.out.println("Sending message:"+msg);
	rabbitTemplate.convertSendAndReceive(
         fanout.getName(), 
         "",   // routing key none.
         msg);
    }

}
