package hu.webuni.gnadigpeti.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.model.Employee;

@Service
public class DefaultEmployeeService implements EmployeeService {

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 5;
	}

}
