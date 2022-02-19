package hu.webuni.gnadigpeti.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.gnadigpeti.model.Company_;
import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.Employee_;
import hu.webuni.gnadigpeti.model.Position_;

public class EmployeeSpecifications {

	public static Specification<Employee> hasId(Long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	public static Specification<Employee> hasName(String name) {
		
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
	}

	public static Specification<Employee> hasSalary(Integer salary) {
		double min = salary * 0.95;
	    double max = salary * 1.05;
		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), (int)min, (int)max);
	}

	public static Specification<Employee> hasStartDate(LocalDateTime startDate) {
		LocalDateTime startDayOnlyDate = LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(0, 0));
		return (root, cq, cb) -> cb.between(root.get(Employee_.startDate), startDate, startDayOnlyDate.plusDays(1));
	}

	public static Specification<Employee> hasCompany(String companyName) {
		return (root, cq, cb) -> cb.like( cb.lower(root.get(Employee_.company).get(Company_.COMPANY_NAME)), companyName.toLowerCase()+"%");
	}

	public static Specification<Employee> hasPosition(String positionName) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), positionName);
	}
}
