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
	private String jobTitle;
	@Positive
	private int salary;
	@Past
    private LocalDateTime startDate;
	
	private CompanyDTO company;
	
    public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(Long id, String name, String jobTitle, int salary, LocalDateTime startDate) {
		super();
		this.id = id;
		this.name = name;
		this.jobTitle = jobTitle;
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



	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", name=" + name + ", jobTitle=" + jobTitle + ", salary=" + salary
				+ ", startDate=" + startDate + ", company=" + company + "]";
	}
    
	
}
