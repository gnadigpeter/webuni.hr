package hu.webuni.gnadigpeti.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hu.webuni.gnadigpeti.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{

	List<Employee> findByPositionName(String jobTitle);

	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByStartDateBetween(LocalDateTime date1, LocalDateTime date2);

	List<Employee> findBySalaryGreaterThan(Integer minSalary);

	List<Employee> findByName(String name);

	@EntityGraph(attributePaths = {"managedEmployee", "manager"})
	Optional<Employee> findByUsername(String username);

}
