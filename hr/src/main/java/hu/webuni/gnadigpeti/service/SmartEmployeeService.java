package hu.webuni.gnadigpeti.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.config.HrConfigProperties;
import hu.webuni.gnadigpeti.model.Employee;

@Service
public class SmartEmployeeService implements EmployeeService {

	@Autowired
	HrConfigProperties config;
	
	@Override
	public int getPayRaisePercent(Employee employee) {

		long year = ChronoUnit.YEARS.between(employee.getStartDate(), LocalDateTime.now());
		/*
		if(year >= 10) {
			return 10;
		}else if (year >=  5){
			return 5;
		}else if(year >= 2.5) {
			return 2;
		}else {
			return 0;
		}*/
		
		if(year >= config.getRaise().getSmart().getYearTen()) {
			return config.getRaise().getSmart().getRaiseTen();
		} else if(year >= config.getRaise().getSmart().getYearFive()) {
			return config.getRaise().getSmart().getRaiseFive();
		}else if(year >= config.getRaise().getSmart().getYearTwoPointFive()) {
			return config.getRaise().getSmart().getRaiseTwo();
		}else {
			return config.getRaise().getSmart().getRaiseZero();
		}
		
	}

}
