package hu.webuni.gnadigpeti.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

//@NamedEntityGraph(name = "Company.full",
//				attributeNodes = @NamedAttributeNode("employees"))	
@NamedEntityGraph(name = "Company.full",
attributeNodes = {
    @NamedAttributeNode("employees"),
    @NamedAttributeNode(value = "employees", subgraph = "employees-subgraph")
},
subgraphs = {
        @NamedSubgraph(name = "employees-subgraph",
            attributeNodes = {
                @NamedAttributeNode("position")
            }
        )
    }
)
    
   
@Entity
public class Company {
	@Id
	@GeneratedValue
	private Long id;
	private String registrationNumber;
	private String companyName;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Employee> employees = new ArrayList<>();
	
	@ManyToOne
	private CompanyType companyType;
	
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
	
	public void addEmployee(Employee employee) {
		if(this.employees == null)
			this.employees = new ArrayList<>();
		this.employees.add(employee);
		employee.setCompany(this);
	}

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}
	
	
}
