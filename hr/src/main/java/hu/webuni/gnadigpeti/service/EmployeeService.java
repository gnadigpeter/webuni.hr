package hu.webuni.gnadigpeti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;

@Service
public interface EmployeeService {
	
	public Employee save(Employee employee);
	
	public Employee update(Employee employee);
	
	public List<Employee> findAll();
	
	public Optional<Employee> findById(long id);
	
	public void delete(long id);

	int getPayRaisePercent(Employee employee);
	
	public List<Employee> findBySalaryGreaterThan(int minSalary);
	
	public List<Employee> findByRank(String jobTitle);
	
	public List<Employee> findByNameStartingWith(String name);
	
	public List<Employee> findByStartDateBetween(String date1, String date2);
}
