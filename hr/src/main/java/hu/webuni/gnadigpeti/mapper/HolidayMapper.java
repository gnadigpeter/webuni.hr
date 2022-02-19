package hu.webuni.gnadigpeti.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.gnadigpeti.dto.HolidayDTO;
import hu.webuni.gnadigpeti.model.Holiday;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

	List<HolidayDTO> holidayRequestsToDtos(List<Holiday> holidayRequests);	
	
	@Mapping(source = "employee.id", target = "employeeId")
	@Mapping(source = "approver.id", target = "approverId")	
	HolidayDTO holidayRequestToDto(Holiday holidayRequest);

	@Mapping(target = "employee", ignore = true)
	@Mapping(target = "approver", ignore = true)
	Holiday dtoToHolidayRequest(@Valid HolidayDTO holidayRequestDto);

	List<Holiday> dtosToHolidayRequests(List<HolidayDTO> holidayRequestDtos);
}
