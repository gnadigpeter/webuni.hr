package hu.webuni.gnadigpeti.web;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
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
import hu.webuni.gnadigpeti.model.AverageSalaryByPosition;
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
		List<Company> companies = null;
		if(isFull(full)) {
			companies = companyService.findAllWithEmployees();
			return companyMapper.companiesToDTOs(companies);
		}else {
			companies = companyService.findAll();
			return companyMapper.companiesToSummaryDTOs(companies);
		}
	}
	
	private boolean isFull(Boolean full) {
		return full != null && full;
	}
	
	@GetMapping("/{id}")
	public CompanyDTO getById(@RequestParam(required=false) Boolean full, @PathVariable Long id){
		Company company = findByIdOrThrow(id);
		if(isFull(full)) {
			return companyMapper.companyToDTO(company);
		}else {
			return companyMapper.companyToSummaryDTO(company);
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
		try {
			return companyMapper.companyToDTO(companyService.addEmployee(id, employeeMapper.dtoToEmployee(employeeDTO)));
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@DeleteMapping("/{id}/employee/{empId}")
	public CompanyDTO deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long empId) {
		try {
			return companyMapper.companyToDTO(companyService.deleteEmployee(id, empId));
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}/employee")
	public CompanyDTO replaceAllEmployees(@PathVariable long id, @RequestBody List<EmployeeDTO> employees) {
		try {
			return companyMapper.companyToDTO(companyService.replaceEmployee(id, employeeMapper.dtosToEmployees(employees)));
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	private Company findByIdOrThrow(long id) {
		return companyService.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping(params = "aboveSalary")
	public List<CompanyDTO> getCompaniesAboveASalary(@RequestParam int aboveSalary,
			@RequestParam(required = false) Boolean full,
			@SortDefault("id") Pageable pageable) {
		Page<Company> page = companyService.findByEmployeeWithSalaryHigherThan(pageable, aboveSalary);
		List<Company> allCompanies = page.getContent();
		return mapCompanies(allCompanies, full);
	}

	private List<CompanyDTO> mapCompanies(List<Company> allCompanies, Boolean full) {
		if (full == null || !full) {
			return companyMapper.companiesToSummaryDTOs(allCompanies);
		} else
			return companyMapper.companiesToDTOs(allCompanies);
	}

	@GetMapping(params = "aboveEmployeeNumber")
	public List<CompanyDTO> getCompaniesAboveEmployeeNumber(@RequestParam int aboveEmployeeNumber,
			@RequestParam(required = false) Boolean full) {
		List<Company> filteredCompanies = companyService.findByEmployeeCountHigherThan(aboveEmployeeNumber);
		return mapCompanies(filteredCompanies, full);
	}
	
	@GetMapping("/{id}/salaryStats")
	public List<AverageSalaryByPosition> getSalaryStatsById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
		return companyService.findAverageSalariesByPosition(id);
	}
	
	
}
