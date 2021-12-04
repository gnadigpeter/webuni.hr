package hu.webuni.gnadigpeti.service;

import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.model.Employee;

@Service
public interface EmployeeService {
	public int getPayRaisePercent(Employee employee);
}
