package hu.webuni.gnadigpeti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.gnadigpeti.service.DefaultEmployeeService;
import hu.webuni.gnadigpeti.service.EmployeeService;

@Configuration
@Profile("!smart")
public class DefaultEmployeeConfiguration {
	
	@Bean
	public EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}

}
