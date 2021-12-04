package hu.webuni.gnadigpeti.service;

import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.model.Employee;

@Service
public class SalaryService {

	EmployeeService employeeService;

	public SalaryService(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	public void setEmployeeSalery(Employee employee) {
		employee.setSalary(employee.getSalary() / 100 * (100+employeeService.getPayRaisePercent(employee)));
	}
	
}
