package hu.webuni.gnadigpeti.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	List<EmployeeDTO> employeesToDTOs(List<Employee> employees);

	EmployeeDTO employeeToDTO(Employee employee);

	Employee dtoToEmployee(EmployeeDTO employeeDTO);

	List<Employee> dtosToEmployees(List<EmployeeDTO> employees);
}
