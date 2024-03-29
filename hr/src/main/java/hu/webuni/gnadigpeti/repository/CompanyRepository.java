package hu.webuni.gnadigpeti.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.gnadigpeti.model.AverageSalaryByPosition;
import hu.webuni.gnadigpeti.model.Company;
import hu.webuni.gnadigpeti.model.Employee;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > :minSalary")
	public Page<Company> findByEmployeeWithSalaryHigherThan(Pageable pageable, int minSalary);
	
	@Query("SELECT c FROM Company c WHERE SIZE(c.employees) > :minEmployeeCount")
	public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);
	
	
	@Query("SELECT e.position.name AS position, avg(e.salary) AS averageSalary "
			+ "FROM Company c "
			+ "INNER JOIN c.employees e "
			+ "WHERE c.id = :companyId "
			+ "GROUP BY e.position.name "
			+ "ORDER BY avg(e.salary) DESC")
	public List<AverageSalaryByPosition> findAverageSalariesByPosition(long companyId);

	public List<Company> findByCompanyName(String string);
	
	
//	@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")
//	@EntityGraph(attributePaths = "employees")
//	@EntityGraph(attributePaths = {"employees", "employees.position"}) 
	@EntityGraph("Company.full")
	@Query("SELECT c FROM Company c")
	public List<Company> findAllWithEmployees();



	
}
