package hu.webuni.airport.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.airport.dto.AirportDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AirportControllerIT {
	
	
	private static final String BASE_URI="/api/airports";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	void testThatCreaedAirportIsListed() throws Exception {
		List<AirportDTO> airportsBefore = getAllAirports();
		
		AirportDTO newAirport = new AirportDTO(5,"név","IGH");
		createAirport(newAirport);
		
		List<AirportDTO> airportsAfter = getAllAirports();
		
		assertThat(airportsAfter.subList(0, airportsBefore.size()))
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(airportsBefore);
		
		assertThat(airportsAfter.get(airportsAfter.size()-1))
		.usingRecursiveComparison()
			.isEqualTo(newAirport);
	}

	private void createAirport(AirportDTO newAirport) {
		webTestClient
			.post()
			.uri(BASE_URI)
			.bodyValue(newAirport)
			.exchange()
			.expectStatus()
			.isOk();
	}
	
	private List<AirportDTO> getAllAirports() {
		List<AirportDTO> responseList = webTestClient
			.get()
			.uri(BASE_URI)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(AirportDTO.class)
			.returnResult().getResponseBody();
		
		Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		
		return responseList;
	}
	
}
