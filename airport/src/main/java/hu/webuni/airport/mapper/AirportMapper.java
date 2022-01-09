package hu.webuni.airport.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.airport.dto.AirportDTO;
import hu.webuni.airport.model.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapper {
	List<AirportDTO> airportsToDTOs(List<Airport> airports);

	AirportDTO airportToDTO(Airport airport);

	Airport dtoToAirport(AirportDTO airportDTO);
}
