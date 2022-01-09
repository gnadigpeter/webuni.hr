package hu.webuni.gnadigpeti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.model.Company;
import hu.webuni.gnadigpeti.model.Employee;

@Service
public class CompanyService {
	private Map<Long, Company> companies = new HashMap<Long, Company>();
	{
		List<Employee> emp = new ArrayList<>();
		emp.add(new Employee(1L, "aaa", "Jani", 0, null));
		emp.add(new Employee(2L, "bbb", "Jani", 0, null));
		emp.add(new Employee(3L, "ccc", "Jani", 0, null));
		companies.put(1L, new Company(1L, "123", "test cp", emp));
	}
	
	public List<Company> findAll(){
		return new ArrayList<>(companies.values());
	}
	
	public Company findById(long id) {
		return companies.get(id);
	}
	
	public Company save(Company company) {
		companies.put(company.getId(), company);
		return company;
	}
	
	public void delete(long id) {
		companies.remove(id);
	}
	
}
