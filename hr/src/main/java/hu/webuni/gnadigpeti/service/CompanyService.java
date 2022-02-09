package hu.webuni.gnadigpeti.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.gnadigpeti.model.AverageSalaryByPosition;
import hu.webuni.gnadigpeti.model.Company;
import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.Position;
import hu.webuni.gnadigpeti.repository.CompanyRepository;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;
import hu.webuni.gnadigpeti.repository.PositionRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PositionRepository positionRepository;
	
	public List<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public List<Company> findAllWithEmployees(){
		return companyRepository.findAllWithEmployees();
	}
	
	public Optional<Company> findById(long id) {
		return companyRepository.findById(id);
	}
	
	@Transactional
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	@Transactional
	public Company update(Company company) {
		if(companyRepository.existsById(company.getId())) {
			return companyRepository.save(company);
		}else {
			throw new NoSuchElementException();
		}
	}
	
	@Transactional
	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
	@Transactional
	public Company addEmployee(long id, Employee employee) {
		Company company = companyRepository.findById(id).get();
		employeeService.save(employee);
		company.addEmployee(employee);
		return company;
	}
	
	
	@Transactional
	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findById(id).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null);
		company.getEmployees().remove(employee);
		employeeRepository.save(employee);
		return company;
	}
	
	@Transactional
	public Company replaceEmployee(long id, List<Employee> employees) {
		Company company = companyRepository.findById(id).get();
		company.getEmployees().forEach(e->e.setCompany(null));
		company.getEmployees().clear();
		
		for(Employee emp: employees) {
			company.addEmployee(emp);
			employeeRepository.save(emp);
		}
		return company;
	}
	
	public Page<Company> findByEmployeeWithSalaryHigherThan(Pageable pageable,int minSalary){
		return companyRepository.findByEmployeeWithSalaryHigherThan(pageable,minSalary);
	}
	
	public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount){
		return companyRepository.findByEmployeeCountHigherThan(minEmployeeCount);
	}
	
	public List<AverageSalaryByPosition> findAverageSalariesByPosition(long id){
		return companyRepository.findAverageSalariesByPosition(id);
	}
	
}
