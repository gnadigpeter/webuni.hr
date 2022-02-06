package hu.webuni.gnadigpeti.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.gnadigpeti.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	List<Employee> findByPositionName(String jobTitle);

	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByStartDateBetween(LocalDateTime date1, LocalDateTime date2);

	List<Employee> findBySalaryGreaterThan(Integer minSalary);

	List<Employee> findByName(String name);
}
