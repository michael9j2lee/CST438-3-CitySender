package cst438_assignment3;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Cst438week2Assignment2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cst438week2Assignment2Application.class, args);
	}
	
	@Bean
	Random getRandom() {
		return new Random();
	}
}
