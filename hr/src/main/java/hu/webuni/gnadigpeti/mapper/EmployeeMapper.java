package hu.webuni.gnadigpeti.mapper;

import java.util.List;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	List<EmployeeDTO> employeesToDTOs(List<Employee> employees);

	@Mapping(target="company.employees",ignore=true)
	EmployeeDTO employeeToDTO(Employee employee);

	@InheritConfiguration
	Employee dtoToEmployee(EmployeeDTO employeeDTO);

	List<Employee> dtosToEmployees(List<EmployeeDTO> employees);
}
