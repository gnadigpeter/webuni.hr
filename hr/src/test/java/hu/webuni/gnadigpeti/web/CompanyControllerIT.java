package hu.webuni.gnadigpeti.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.gnadigpeti.dto.CompanyDTO;
import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.repository.CompanyRepository;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyControllerIT {
	private static final String BASE_COMPANY_URI="/api/companies";
	private static final String BASE_EMPLOYEE_URI="/api/employees";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@BeforeEach
	public void init() {
		companyRepository.deleteAll();
		employeeRepository.deleteAll();
	}
	
	
	@Test
	void testCreateCompany() throws Exception {
		List<CompanyDTO> companiesBefore = getAllCompany();		
		CompanyDTO newCompanyDTO = createCompanyDTO();
		createCompany(newCompanyDTO);			
		List<CompanyDTO> companiesAfter = getAllCompany();	
		
		assertThat(companiesAfter.subList(0, companiesBefore.size()))
		.usingRecursiveFieldByFieldElementComparator()
		.containsExactlyElementsOf(companiesBefore);
			
		assertThat(companiesAfter.get(companiesAfter.size()-1))
		.usingRecursiveComparison()
		.ignoringFields("id")
		.isEqualTo(newCompanyDTO);
	}
	
	
	
	@Test
	void testAddEmployeeToCompany() throws Exception {
		List<CompanyDTO> companiesBefore = getAllCompany();		
		CompanyDTO newCompanyDTO = createCompany(createCompanyDTO());	;
		List<CompanyDTO> companiesAfter = getAllCompany();
		EmployeeDTO employee1 = newValidEmployee();
		
		List<CompanyDTO> newCompanyDTO2 =  addEmployeeToCompany(newCompanyDTO.getId(),employee1);
		
		
		
		
		
		assertThat(companiesAfter.subList(0, companiesBefore.size()))
		.usingRecursiveFieldByFieldElementComparator()
		.containsExactlyElementsOf(companiesBefore);
		
		System.out.println(companiesAfter.get(0).getCompanyName());
		
		//TODO: assert! 
		
		
	}
	
	private EmployeeDTO newValidEmployee() {
		return  new EmployeeDTO(null, "Józsi", "test", 500, LocalDateTime.of(2020, 1, 1, 1, 1));
	}
	
	private CompanyDTO createCompanyDTO() {
		List<EmployeeDTO> employees = new ArrayList<>();
		return  new CompanyDTO(1L, "A123", "Evil Company", employees);
	}
	
	private CompanyDTO createCompany(CompanyDTO newCompany) {
		return webTestClient
			.post()
			.uri(BASE_COMPANY_URI)
			.bodyValue(newCompany)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(CompanyDTO.class)
			.returnResult()
			.getResponseBody();
	}
	
	private List<CompanyDTO> getAllCompany() {
		List<CompanyDTO> responseList = webTestClient
				.get()
				.uri(BASE_COMPANY_URI)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(CompanyDTO.class)
				.returnResult()
				.getResponseBody();
		Collections.sort(responseList, Comparator.comparing(CompanyDTO::getId));
		
		return responseList;
	}
	
	private List<CompanyDTO> addEmployeeToCompany(long companId, EmployeeDTO employeeDTO) {
		List<CompanyDTO> responseList = webTestClient
				.post()
				.uri(BASE_COMPANY_URI+"/"+companId+"/employee")
				.bodyValue(employeeDTO)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(CompanyDTO.class)
				.returnResult()
				.getResponseBody();
		Collections.sort(responseList, Comparator.comparing(CompanyDTO::getId));
		
		return responseList;
		
	}
	
	private void createEmployee(EmployeeDTO newEmployeeDTO) {
		webTestClient
			.post()
			.uri(BASE_EMPLOYEE_URI)
			.bodyValue(newEmployeeDTO)
			.exchange()
			.expectStatus()
			.isOk();
	}
	
	
	
}
