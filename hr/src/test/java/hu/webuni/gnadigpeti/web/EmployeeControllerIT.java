package hu.webuni.gnadigpeti.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
	
	private static final String BASE_URI="/api/employees";
	
	@Autowired
	WebTestClient webTestClient;

	@Test
	void testThatCreaedEmployeeIsListed() throws Exception {
		List<EmployeeDTO> employeesBefore = getAllEmployee();

		EmployeeDTO newEmployeeDTO = new EmployeeDTO(5L, "Józsi", "CEO", 500, LocalDateTime.of(2020, 1, 1, 12, 12));
		createEmployee(newEmployeeDTO);
		
		List<EmployeeDTO> employeesAfter = getAllEmployee();
		
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
		.usingRecursiveFieldByFieldElementComparator()
		.containsExactlyElementsOf(employeesBefore);
	
		assertThat(employeesAfter.get(employeesAfter.size()-1))
		.usingRecursiveComparison()
			.isEqualTo(newEmployeeDTO);
		
	}
	
	@Test
	void testEditEmployee() throws Exception {
		
	}

	private void createEmployee(EmployeeDTO newEmployeeDTO) {
		webTestClient
			.post()
			.uri(BASE_URI)
			.bodyValue(newEmployeeDTO)
			.exchange()
			.expectStatus()
			.isOk();
	}

	private List<EmployeeDTO> getAllEmployee() {
		List<EmployeeDTO> responseList = webTestClient
				.get()
				.uri(BASE_URI)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(EmployeeDTO.class)
				.returnResult().getResponseBody();
		Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		
		return responseList;
	}
	
}	

