package hu.webuni.gnadigpeti.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;

public abstract class EmployeeServiceA {

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public List<Employee> findAll(){
		return employeeRepository.findAll();
	}
	
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}
	
	@Transactional
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employee update(Employee employee) {
		if(employeeRepository.existsById(employee.getId())) {
			return employeeRepository.save(employee);
		}else {
			throw new NoSuchElementException();
		}
	}
	@Transactional
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}
	
	
	public List<Employee> findByRank(String rank){
		return employeeRepository.findDistinctByRank(rank);
	}
	
	public List<Employee> findByNameStartingWith(String name){
		return employeeRepository.findByNameStartingWithIgnoreCase(name);
	}
	
	public List<Employee> findByStartDateBetween(String date1, String date2){
		LocalDateTime localDateTime1 = LocalDate.parse(date1).atStartOfDay();
		LocalDateTime localDateTime2 = LocalDate.parse(date2).atStartOfDay();
		return employeeRepository.findByStartDateBetween(localDateTime1, localDateTime2);
	}
	
}
