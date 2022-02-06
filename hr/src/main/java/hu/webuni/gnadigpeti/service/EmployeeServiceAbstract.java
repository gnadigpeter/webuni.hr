package hu.webuni.gnadigpeti.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.Position;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;
import hu.webuni.gnadigpeti.repository.PositionRepository;

@Service
public abstract class EmployeeServiceAbstract implements EmployeeService{

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	private PositionRepository positionRepository;
	
	
	private void clearCompanyAndSetPosition(Employee employee) {
		//employee.setCompany(null);
		Position position = null;
		String positionName = employee.getPosition().getName();
		if(positionName != null) {
			List<Position> positions = positionRepository.findByName(positionName);
			if(positions.isEmpty()) {
				position = positionRepository.save(new Position(positionName, null));
			} else {
				position = positions.get(0);
			}
		}
		employee.setPosition(position);
	}
	
	public List<Employee> findAll(){
		return employeeRepository.findAll();
	}
	
	public List<Employee> findBySalaryGreaterThan(int minSalary){
		return employeeRepository.findBySalaryGreaterThan(minSalary);
	}
	
	
	
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}
	
	@Transactional
	public Employee save(Employee employee) {
		clearCompanyAndSetPosition(employee);
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employee update(Employee employee) {
		if(!employeeRepository.existsById(employee.getId()))
			return null;
		
		clearCompanyAndSetPosition(employee);
		return employeeRepository.save(employee);
	}
	@Transactional
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}
	
	
	
	public List<Employee> findByRank(String jobTitle){
		return employeeRepository.findByPositionName(jobTitle);
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
