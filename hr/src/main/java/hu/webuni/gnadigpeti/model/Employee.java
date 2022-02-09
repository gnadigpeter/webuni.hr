package hu.webuni.gnadigpeti.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	//private String jobTitle;
	int salary;
    LocalDateTime startDate;
	
    @ManyToOne
    private Company company;
    
    @ManyToOne
    //@Cascade(CascadeType.SAVE_UPDATE)
    private Position position;
    
    
    public Employee() {
		super();
	}

	public Employee(Long id, String name,  int salary, LocalDateTime firstWorkingDay) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = firstWorkingDay;
	}
	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	
	
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", startDate=" + startDate
				+ ", company=" + company + ", positionName=" + position.getName() + "]";
	}

	
	
	
    
}
