package hu.webuni.gnadigpeti.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDTO {
	private Long id;
	private String registrationNumber;
	private String companyName;
	
	List<EmployeeDTO> employees = new ArrayList<>();

	public CompanyDTO() {
	}

	public CompanyDTO(Long id, String registrationNumber, String companyName, List<EmployeeDTO> employees) {
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

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDTO> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", registrationNumber=" + registrationNumber + ", companyName=" + companyName
				+ ", employees=" + employees.toString() + "]";
	}
	
	
	
	
}


