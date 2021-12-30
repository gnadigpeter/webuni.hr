package hu.webuni.gnadigpeti.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.gnadigpeti.dto.CompanyDTO;
import hu.webuni.gnadigpeti.dto.EmployeeDTO;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	private Map<Long, CompanyDTO> companies = new HashMap<Long, CompanyDTO>();
	
	{
		List<EmployeeDTO> emp = new ArrayList<>();
		emp.add(new EmployeeDTO(1L, "aaa", "Jani", 0, null));
		emp.add(new EmployeeDTO(2L, "bbb", "Jani", 0, null));
		emp.add(new EmployeeDTO(3L, "ccc", "Jani", 0, null));
		companies.put(1L, new CompanyDTO(1L, "123", "test cp", emp));
	}
	
	@GetMapping()
	public List<CompanyDTO> getAll(@RequestParam(required=false) Boolean full){
		if(isFull(full)) {
			return new ArrayList<>(companies.values());
		}else {
			return companies.values()
					.stream()
					.map(this::createCompanyWithoutEmployees)
					.collect(Collectors.toList());
		}
		
	}

	private CompanyDTO createCompanyWithoutEmployees(CompanyDTO c) {
		return new CompanyDTO(c.getId(), c.getCompanyName(), c.getRegistrationNumber(), null);
	}
	
	private boolean isFull(Boolean full) {
		return full != null && full;
	}
	
	@GetMapping("/{id}")
	public CompanyDTO getById(@RequestParam(required=false) Boolean full, @PathVariable Long id){
		CompanyDTO companyDTO = companies.get(id);
		if(isFull(full)) {
			return companyDTO;
		}else {
			return createCompanyWithoutEmployees(companyDTO);
		}
	}
	
	@PostMapping
	public CompanyDTO createCompany(@RequestBody CompanyDTO companyDTO) {
		companies.put(companyDTO.getId(), companyDTO);
		return companyDTO;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDTO> modifyCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO){
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		companyDTO.setId(id);
		companies.put(id, companyDTO);
		return ResponseEntity.ok(companyDTO);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companies.remove(id);
	}
	
	@PostMapping("/{id}/employee")
	public CompanyDTO addNewEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeDTO){
		CompanyDTO company = findByIdOrThrow(id);
		company.getEmployees().add(employeeDTO);
		return company;
	}

	private CompanyDTO findByIdOrThrow(long id) {
		CompanyDTO company =  companies.get(id);
		if(company == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return company;
	}
	
	@DeleteMapping("/{id}/employee/{empId}")
	public CompanyDTO deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long empId) {
		CompanyDTO company = findByIdOrThrow(id);
		company.getEmployees().removeIf(emp -> emp.getId() == empId);
		return company;
	}
	
	@PutMapping("/{id}/employee")
	public CompanyDTO replaceAllEmployees(@RequestBody List<EmployeeDTO> employees, @PathVariable long id) {
		CompanyDTO company = findByIdOrThrow(id);
		company.setEmployees(employees);
		return company;
	}
	
	
}
