package hu.webuni.gnadigpeti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.gnadigpeti.service.EmployeeService;
import hu.webuni.gnadigpeti.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartEmployeeConfiguration {

	@Bean
	public EmployeeService employeeService() {
		return new SmartEmployeeService();
	}
}
