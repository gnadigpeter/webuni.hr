package hu.webuni.airport.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.airport.model.Airport;
import hu.webuni.airport.model.Flight;
import hu.webuni.airport.repository.AirportRepository;
import hu.webuni.airport.repository.FlightRepository;

@SpringBootTest
@AutoConfigureTestDatabase
public class AirportServiceIT {

	@Autowired
	AirportService airportService;
	
	@Autowired
	AirportRepository airportRepository;
	
	@Autowired
	FlightRepository flightRepository;
	
	
	
	@Test
	void testCreateFlight() throws Exception {
		String flightnumber = "AAA";
		long takeoff = airportRepository.save(new Airport("airport1", "iata1")).getId();
		long landing = airportRepository.save(new Airport("airport2", "iata2")).getId();
		LocalDateTime dateTime = LocalDateTime.now();
		Flight flight = airportService.createFlight(flightnumber, takeoff, landing, dateTime);
		
		Optional<Flight> savedFlightOptional = flightRepository.findById(flight.getId());
		assertThat(savedFlightOptional).isNotEmpty();
		assertThat(savedFlightOptional.get().getFlightNumber()).isEqualTo(flightnumber);
		assertThat(savedFlightOptional.get().getTakeoffTime()).isCloseTo(dateTime, within(1, ChronoUnit.MICROS)); //new TemporalUnitWithinOffset(1, ChronoUnit.MICROS)
		assertThat(savedFlightOptional.get().getTakeoff().getId()).isEqualTo(takeoff);
		assertThat(savedFlightOptional.get().getLanding().getId()).isEqualTo(landing);
	}
	
}
