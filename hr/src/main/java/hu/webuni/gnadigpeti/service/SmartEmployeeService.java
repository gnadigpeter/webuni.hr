package hu.webuni.gnadigpeti.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.config.HrConfigProperties;
import hu.webuni.gnadigpeti.config.HrConfigProperties.Smart;
import hu.webuni.gnadigpeti.model.Employee;

@Service
public class SmartEmployeeService extends EmployeeServiceAbstract implements EmployeeService {

	@Autowired
	HrConfigProperties config;
	
	@Override
	public int getPayRaisePercent(Employee employee) {

		double yearsWorked = ChronoUnit.DAYS.between(employee.getStartDate(), LocalDateTime.now()) /365.0;

		Smart smartConfig = config.getSalary().getSmart();
		
		/*
		if(yearsWorked >= smartConfig.getLimit3()) {
			return smartConfig.getPercent3();
		} else if(yearsWorked >= smartConfig.getLimit2()) {
			return smartConfig.getPercent2();
		}else if(yearsWorked >= smartConfig.getLimit1()) {
			return smartConfig.getPercent1();
		}else {
			return 0;
		}*/
		
		
		TreeMap<Double, Integer> raisingIntervals= smartConfig.getLimits();
		
		//1. megoldás
//		Integer maxLimit = null;
//		for(Entry<Double, Integer> entry : raisingIntervals.entrySet()) {
//			
//			if(yearsWorked > entry.getKey()) {
//				maxLimit= entry.getValue();
//			}else {
//				break;
//			}
//		}
//		return maxLimit == null? 0: maxLimit;
		
		//2. megoldás streammel
//		Optional<Double> optionalMax = raisingIntervals.keySet().stream()
//			.filter(k -> yearsWorked >= k)
//			.max(Double::compare);
//		
//		return optionalMax.isEmpty() ? 0 : raisingIntervals.get(optionalMax.get());
		
		//3. megoldás
		Entry<Double, Integer> floorEntry = raisingIntervals.floorEntry(yearsWorked);
		return floorEntry == null? 0 : floorEntry.getValue();
	}

	

}
