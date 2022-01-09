package hu.webuni.gnadigpeti.model;

import java.util.ArrayList;
import java.util.List;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;

public class Company {
	private Long id;
	private String registrationNumber;
	private String companyName;
	
	List<Employee> employees = new ArrayList<>();

	public Company() {
	}

	public Company(Long id, String registrationNumber, String companyName, List<Employee> employees) {
		super();
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.companyName = companyName;
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
