package hu.webuni.gnadigpeti.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.gnadigpeti.dto.CompanyDTO;
import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.repository.CompanyRepository;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
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
		CompanyDTO newCompanyDTO = createCompany(createCompanyDTO());
		EmployeeDTO newEmployeeDTO = newValidEmployee();
		List<EmployeeDTO> employeesBefore = newCompanyDTO.getEmployees();	
		newCompanyDTO = addEmployeeToCompany(newCompanyDTO.getId(),newEmployeeDTO);	
		List<EmployeeDTO> employeesAfter = newCompanyDTO.getEmployees();
		
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(employeesBefore);
		assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size() + 1);
		assertThat(employeesAfter.get(employeesAfter.size() - 1))
	        .usingRecursiveComparison()
	        .ignoringFields("id")
	        .isEqualTo(newEmployeeDTO);
	}
	
	
	@Test
	void testDeleteEmployeeFromCompany() throws Exception {
		CompanyDTO newCompanyDTO = createCompany(createCompanyDTO());
		EmployeeDTO newEmployeeDTO = newValidEmployee();
		newCompanyDTO = addEmployeeToCompany(newCompanyDTO.getId(),newValidEmployee());	
		newEmployeeDTO = newCompanyDTO.getEmployees().get(0);
		List<EmployeeDTO> employeesBefore = newCompanyDTO.getEmployees();

		deleteEmployeeFromCompany(newCompanyDTO.getId(), newEmployeeDTO.getId());
		List<EmployeeDTO> employeesAfter = getCompanyById(newCompanyDTO.getId()).getEmployees();
		
//		System.out.println("employees after delete: ");
//		System.out.println(employeesAfter.size());
//		for(EmployeeDTO e : employeesAfter) {
//			System.out.println(e.getName());
//		}
		
		assertThat(employeesAfter.size()+1).isEqualTo(employeesBefore.size());
		assertThat(employeesBefore)
			.contains(newEmployeeDTO);
		assertThat(employeesAfter)
			.doesNotContain(newEmployeeDTO);	
	}
	
	
	@Test
	void testReplaceAllEmployee() throws Exception {
		CompanyDTO newCompanyDTO = createCompany(createCompanyDTO());
		List<EmployeeDTO> employeeList = new ArrayList<>();
		
		for(int i=0;i<3;i++) {
			employeeList.add(new EmployeeDTO(null, i+1+" Józsi", "test", 500, LocalDateTime.of(2020, 1, 1, 1, 1)));
		}
		CompanyDTO newCompanyDTO2 = replaceEmployeesToCompany(newCompanyDTO.getId(), employeeList);
		
		assertThat(newCompanyDTO.getEmployees().size() +3).isEqualTo(newCompanyDTO2.getEmployees().size());
		assertThat(newCompanyDTO2.getEmployees())
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(employeeList);
		
	}
	
	private EmployeeDTO newValidEmployee() {
		return  new EmployeeDTO(null, "Józsi", "test", 500, LocalDateTime.of(2020, 1, 1, 1, 1));
	}
	
	private CompanyDTO createCompanyDTO() {
		List<EmployeeDTO> employees = new ArrayList<>();
		return  new CompanyDTO(null, "A123", "Evil Company", employees);
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
	
	private CompanyDTO addEmployeeToCompany(long companId, EmployeeDTO employeeDTO) {
		return webTestClient
				.post()
				.uri(BASE_COMPANY_URI+"/"+companId+"/employee")
				.bodyValue(employeeDTO)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(CompanyDTO.class)
				.returnResult()
				.getResponseBody();	
	}
	
	private CompanyDTO replaceEmployeesToCompany(long companId, List<EmployeeDTO> employeeDTOs) {
		return webTestClient
				.put()
				.uri(BASE_COMPANY_URI+"/"+companId+"/employee")
				.bodyValue(employeeDTOs)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(CompanyDTO.class)
				.returnResult()
				.getResponseBody();	
	}
	
	
	private void deleteEmployeeFromCompany(long companyId, long employeeId) {
		webTestClient
		.delete()
		.uri(BASE_COMPANY_URI+"/"+companyId+"/employee/"+employeeId)
		.exchange()
		.expectStatus()
		.isOk();
	}
	
	private CompanyDTO getCompanyById(long id) {
		return webTestClient
				.get()
				.uri(BASE_COMPANY_URI+"/"+id+"?full=true")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(CompanyDTO.class)
				.returnResult()
				.getResponseBody();
	}
	
	
}
