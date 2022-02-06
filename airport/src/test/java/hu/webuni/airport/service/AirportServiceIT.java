package hu.webuni.airport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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
	
	@BeforeEach
	public void init() {
		flightRepository.deleteAll();
		airportRepository.deleteAll();
	}
	
	@Test
	void testCreateFlight() throws Exception {
		String flightnumber = "ABC123";
		long takeoff = createAirport("airport1", "iata1");
		long landing = createAirport("airport2", "iata2");
		LocalDateTime dateTime = LocalDateTime.now();
		long flightId = createFlight(flightnumber, takeoff, landing, dateTime);
		
		Optional<Flight> savedFlightOptional = flightRepository.findById(flightId);
		assertThat(savedFlightOptional).isNotEmpty();
		assertThat(savedFlightOptional.get().getFlightNumber()).isEqualTo(flightnumber);
		assertThat(savedFlightOptional.get().getTakeoffTime()).isCloseTo(dateTime, within(1, ChronoUnit.MICROS)); //new TemporalUnitWithinOffset(1, ChronoUnit.MICROS)
		assertThat(savedFlightOptional.get().getTakeoff().getId()).isEqualTo(takeoff);
		assertThat(savedFlightOptional.get().getLanding().getId()).isEqualTo(landing);
	}

	private long createAirport(String name, String iata) {
		return airportRepository.save(new Airport(name, iata)).getId();
	}
	
	@Test
	void testFindFlightsByExample() throws Exception {
		long airport1Id = createAirport("airport1", "iata1");
		long airport2Id = createAirport("airport2", "iata2");
		long airport3Id = createAirport("airport3", "2iata");
		long airport4Id = createAirport("airport4", "3iata1");
		LocalDateTime takeoff = LocalDateTime.of(2022,2,6, 8, 0, 0);
		long flight1 = createFlight("ABC123", airport1Id, airport3Id, takeoff);
		long flight2 = createFlight("ABC1234", airport2Id, airport3Id, takeoff.plusHours(2));
		long flight3 = createFlight("BC123", airport1Id, airport3Id, takeoff);
		long flight4 = createFlight("ABC123", airport1Id, airport3Id, takeoff.plusDays(1));
		
		createFlight("ABC123", airport3Id, airport3Id, takeoff);
		
		
		Flight example = new Flight();
		example.setFlightNumber("ABC123");
		example.setTakeoffTime(takeoff);
		example.setTakeoff(new Airport("sasa","iata"));
		List<Flight> foundFlights = this.airportService.findFlightsByExample(example);
		
		assertThat(foundFlights.stream().map(Flight::getId).collect(Collectors.toList())).containsExactly(flight1, flight2);
	}

	private long createFlight(String flightnumber, long takeoff, long landing, LocalDateTime dateTime) {
		return airportService.createFlight(flightnumber, takeoff, landing, dateTime).getId();
		
	}
}
