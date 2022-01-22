package hu.webuni.gnadigpeti.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String rank;
	int salary;
    LocalDateTime startDate;
	
    
    
    public Employee() {
		super();
	}

	public Employee(Long id, String name, String rank, int salary, LocalDateTime firstWorkingDay) {
		super();
		this.id = id;
		this.name = name;
		this.rank = rank;
		this.salary = salary;
		this.startDate = firstWorkingDay;
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", rank=" + rank + ", salary=" + salary + ", startDate="
				+ startDate + "]";
	}
	
	
    
}
