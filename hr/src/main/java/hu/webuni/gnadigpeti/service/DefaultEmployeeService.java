package hu.webuni.gnadigpeti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.config.HrConfigProperties;
import hu.webuni.gnadigpeti.model.Employee;

@Service
public class DefaultEmployeeService extends EmployeeServiceA implements EmployeeService {

	@Autowired
	HrConfigProperties configProperties;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		return configProperties.getSalary().getDef().getPercent();
	}

}
