package hu.webuni.gnadigpeti.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import hu.webuni.gnadigpeti.mapper.CompanyMapper;
import hu.webuni.gnadigpeti.mapper.EmployeeMapper;
import hu.webuni.gnadigpeti.model.Company;
import hu.webuni.gnadigpeti.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyMapper companyMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;

	@GetMapping()
	public List<CompanyDTO> getAll(@RequestParam(required=false) Boolean full){
		if(isFull(full)) {
			return companyMapper.companiesToDTOs(companyService.findAll());
		}else {
			return companyMapper.companiesToDTOs(companyService.findAll())
					.stream()
					.map(this::createCompanyWithoutEmployees)
					.collect(Collectors.toList());
		}
	}
	
	private boolean isFull(Boolean full) {
		return full != null && full;
	}
	
	private CompanyDTO createCompanyWithoutEmployees(CompanyDTO c) {
		return new CompanyDTO(c.getId(), c.getCompanyName(), c.getRegistrationNumber(), null);
	}
	
	@GetMapping("/{id}")
	public CompanyDTO getById(@RequestParam(required=false) Boolean full, @PathVariable Long id){
		Company company = findByIdOrThrow(id);
		if(company != null) {
			if(isFull(full)) {
				return companyMapper.companyToDTO(company);
			}else {
				return createCompanyWithoutEmployees(companyMapper.companyToDTO(company));
			}
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public CompanyDTO createCompany(@RequestBody CompanyDTO companyDTO) {
		Company company = companyService.save(companyMapper.dtoToCompany(companyDTO));
		return companyMapper.companyToDTO(company);
	}
	
	@PutMapping("/{id}")
	public CompanyDTO modifyCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO){
		Company company = companyMapper.dtoToCompany(companyDTO);
		company.setId(id);
		try {
			CompanyDTO saveCompanyDTO = companyMapper.companyToDTO(companyService.update(company));
			return saveCompanyDTO;
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companyService.delete(id);
	}
	
	@PostMapping("/{id}/employee")
	public CompanyDTO addNewEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeDTO){
		Company company = findByIdOrThrow(id);
		company.getEmployees().add(employeeMapper.dtoToEmployee(employeeDTO));
		companyService.save(company);
		
		return companyMapper.companyToDTO(company);
	}
	
	private Company findByIdOrThrow(long id) {
		return companyService.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}/employee/{empId}")
	public CompanyDTO deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long empId) {
		Company company = findByIdOrThrow(id);
		company.getEmployees().removeIf(emp -> emp.getId() == empId);
		companyService.save(company);
		return companyMapper.companyToDTO(company);
	}
	
	@PutMapping("/{id}/employee")
	public CompanyDTO replaceAllEmployees(@RequestBody List<EmployeeDTO> employees, @PathVariable long id) {
		Company company = findByIdOrThrow(id);
		company.setEmployees(employeeMapper.dtosToEmployees(employees));
		companyService.save(company);
		return companyMapper.companyToDTO(company);
	}
	
	
	/*
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
//----------------------------------------------------------
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
	}*/
	
	
}
