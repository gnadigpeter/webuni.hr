package hu.webuni.airport.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.airport.dto.AirportDTO;
import hu.webuni.airport.mapper.AirportMapper;
import hu.webuni.airport.model.Airport;
import hu.webuni.airport.service.AirportService;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
	
	@Autowired
	AirportService airportService;
	
	@Autowired
	AirportMapper airportMapper;
	
	
	@GetMapping()
	public List<AirportDTO> getAll(){
		return airportMapper.airportsToDTOs(airportService.findAll());
	}
	
	@GetMapping("/{id}")
	public AirportDTO getById(@PathVariable Long id) {
		Airport airport = airportService.findById(id)
				.orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return airportMapper.airportToDTO(airport);
	}

	@PostMapping
	public AirportDTO createAirport(@RequestBody @Valid AirportDTO airportDTO) {
		Airport airport = airportService.save(airportMapper.dtoToAirport(airportDTO));
		return airportMapper.airportToDTO(airport);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AirportDTO> modifyAirport(@PathVariable Long id, @RequestBody @Valid AirportDTO airportDTO) {
		Airport airport = airportMapper.dtoToAirport(airportDTO);
		airport.setId(id);
		try {
			AirportDTO savedAirportDTO = airportMapper.airportToDTO(airportService.update(airport));
			return ResponseEntity.ok(savedAirportDTO);
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	


	@DeleteMapping("/{id}")
	public void deleteAirport(@PathVariable Long id) {
		airportService.delete(id);
	}
	
	
	
	
}
