package hu.webuni.gnadigpeti.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
	
	private static final String BASE_URI="/api/employees";
	
	@Autowired
	WebTestClient webTestClient;

	@Test
	void testThatCreaedEmployeeIsListed() throws Exception {
		List<EmployeeDTO> employeesBefore = getAllEmployee();

		EmployeeDTO newEmployeeDTO = newValidEmployee();
		createEmployee(newEmployeeDTO);
		
		List<EmployeeDTO> employeesAfter = getAllEmployee();
		
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
		.usingRecursiveFieldByFieldElementComparator()
		.containsExactlyElementsOf(employeesBefore);
	
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(newEmployeeDTO);
		
	}
	
	@Test
	void testThatNewValidEmployeeCanBeSaved() throws Exception {
		List<EmployeeDTO> employeesBefore = getAllEmployee();
		
		EmployeeDTO newEmployee = newValidEmployee();
		saveEmployee(newEmployee)
			.expectStatus()
			.isOk();
		
		List<EmployeeDTO> employeesAfter = getAllEmployee();
		
		assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size()+1);
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(newEmployee);
	}
	
	@Test
	void testThatNewInvalidEmployeeCannotBeSaved() throws Exception {
		List<EmployeeDTO> employeesBefore = getAllEmployee();
		
		EmployeeDTO newEmployee = newInvalidEmployee();
		saveEmployee(newEmployee)
			.expectStatus()
			.isBadRequest();
		
		List<EmployeeDTO> employeesAfter = getAllEmployee();
		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		
	}
	
	@Test
	void testTbatEmployeeCanBeUpdatedWithValidFields() throws Exception {
		EmployeeDTO newEmployeeDTO = newValidEmployee();
		EmployeeDTO savedEmployeeDTO = saveEmployee(newEmployeeDTO)
				.expectStatus()
				.isOk()
				.expectBody(EmployeeDTO.class)
				.returnResult()
				.getResponseBody();
		
		List<EmployeeDTO> employeesBefore = getAllEmployee();
		savedEmployeeDTO.setName("modified");
		modifyEmployee(savedEmployeeDTO).expectStatus().isOk();
		
		List<EmployeeDTO> employeesAfter = getAllEmployee();
		
		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.usingRecursiveComparison()
			.isEqualTo(savedEmployeeDTO);
	}
	
	@Test
	void testTbatEmployeeCannotBeUpdatedWithInvalidFields() throws Exception {
		EmployeeDTO newEmployeeDTO = newValidEmployee();
		EmployeeDTO savedEmployeeDTO = saveEmployee(newEmployeeDTO)
				.expectStatus()
				.isOk()
				.expectBody(EmployeeDTO.class)
				.returnResult()
				.getResponseBody();
		
		List<EmployeeDTO> employeesBefore = getAllEmployee();
		EmployeeDTO invalidEmployee = newInvalidEmployee();
		invalidEmployee.setId(savedEmployeeDTO.getId());
		modifyEmployee(invalidEmployee).expectStatus().isBadRequest();
		
		List<EmployeeDTO> employeesAfter = getAllEmployee();
		
		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.usingRecursiveComparison()
			.isEqualTo(savedEmployeeDTO);
	}

	private EmployeeDTO newInvalidEmployee() {
		return new EmployeeDTO(5L, "", "test", 500, LocalDateTime.of(2020, 1, 1, 12, 12));
	}
	
	private EmployeeDTO newValidEmployee() {
		return  new EmployeeDTO(5L, "Józsi", "test", 500, LocalDateTime.of(2020, 1, 1, 12, 12));
	}
	
	

	private ResponseSpec saveEmployee(EmployeeDTO newEmployee) {
		return webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(newEmployee)
				.exchange();
	}
	private ResponseSpec modifyEmployee(EmployeeDTO newEmployee) {
		String path = BASE_URI + "/"+ newEmployee.getId();
		return webTestClient
				.put()
				.uri(path)
				.bodyValue(newEmployee)
				.exchange();
		
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
				.expectStatus()
				.isOk()
				.expectBodyList(EmployeeDTO.class)
				.returnResult()
				.getResponseBody();
		//Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		Collections.sort(responseList, Comparator.comparing(EmployeeDTO::getId));
		
		return responseList;
	}
	
}	

