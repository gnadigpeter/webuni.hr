package hu.webuni.gnadigpeti.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.gnadigpeti.dto.CompanyDTO;
import hu.webuni.gnadigpeti.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	List<CompanyDTO> companiesToDTOs(List<Company> findAll);

	CompanyDTO companyToDTO(Company company);

	Company dtoToCompany(CompanyDTO companyDTO);

}
