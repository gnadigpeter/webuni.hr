package hu.webuni.gnadigpeti.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class EmployeeDTO {
	
	private Long id;
	@NotNull
	@Size(min=1)
	private String name;
	@NotNull
	@Size(min=1)
	private String rank;
	@Positive
	private int salary;
	@Past
    private LocalDateTime startDate;
	
	private CompanyDTO company;
	
    public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(Long id, String name, String rank, int salary, LocalDateTime startDate) {
		super();
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.salary = salary;
		this.startDate = startDate;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO companyDTO) {
		this.company = companyDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
    
}
