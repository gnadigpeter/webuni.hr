package hu.webuni.gnadigpeti.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.webuni.gnadigpeti.model.Employee;

public abstract class EmployeeServiceA {
	private Map<Long, Employee> employees = new HashMap<Long, Employee>();
	{
		employees.put(1L, new Employee(1L, "Sebastian Silverstone", "brewer", 3000, LocalDateTime.of(2018, 12, 11, 12, 00)));
		employees.put(2L, new Employee(2L, "Leviticus", "actor", 1500, LocalDateTime.of(2018, 12, 11, 12, 00)));
		employees.put(3L, new Employee(3L, "Remi Malcor", "botanist", 4500, LocalDateTime.of(2018, 12, 11, 12, 00)));
	}
	
	public List<Employee> findAll(){
		return new ArrayList<>(employees.values());
	}
	
	public Employee findById(long id) {
		return employees.get(id);
	}
	
	public Employee save(Employee employee) {
		employees.put(employee.getId(), employee);
		return employee;
	}
	
	public void delete(long id) {
		employees.remove(id);
	}
}
