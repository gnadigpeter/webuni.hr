package hu.webuni.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hu.webuni.airport.service.AirportService;
import hu.webuni.airport.service.DefaultDiscountService;
import hu.webuni.airport.service.DiscountSevice;
import hu.webuni.airport.service.InitDbService;
import hu.webuni.airport.service.PriceService;
import hu.webuni.airport.service.SpecialDiscountService;

@SpringBootApplication
public class AirportApplication implements CommandLineRunner{

	@Autowired
	PriceService priceService;
	
	@Autowired
	AirportService airportService;
	
	@Autowired
	InitDbService initDbService;
	
	public static void main(String[] args) {
		SpringApplication.run(AirportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//airportService.createFlight();
		System.out.println(priceService.getFinalPrice(200));
		System.out.println(priceService.getFinalPrice(20000));
		initDbService.createUsersIfNeeded();
	}
	
	

}
