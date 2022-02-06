package hu.webuni.gnadigpeti.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.gnadigpeti.dto.CompanyDTO;
import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.model.Company;
import hu.webuni.gnadigpeti.model.Employee;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	List<CompanyDTO> companiesToDTOs(List<Company> companies);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CompanyDTO> companiesToSummaryDTOs(List<Company> companies);
	
	List<Company> dtosToComponies(List<CompanyDTO> companyDTOs);

	CompanyDTO companyToDTO(Company company);
	
	@Mapping(target ="employees",ignore=true)
	@Named("summary")
	CompanyDTO companyToSummaryDTO(Company company);

	Company dtoToCompany(CompanyDTO companyDTO);
	
	@Mapping(target = "company", ignore = true)
	@Mapping(target = "jobTitle", source="position.name")
	EmployeeDTO employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDTO employeeDto);

}
