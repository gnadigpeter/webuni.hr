package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.airport.dto.AirportDTO;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
	
	private Map<Long, AirportDTO> airports  = new HashMap<Long, AirportDTO>();
	
	{
		airports.put(1L, new AirportDTO(1, "abc","XYZ"));
		airports.put(2L, new AirportDTO(2, "def","UVW"));
	}
	
	@GetMapping()
	public List<AirportDTO> getAll(){
		return new ArrayList<>(airports.values());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AirportDTO> getById(@PathVariable Long id) {
		AirportDTO airportDTO = airports.get(id);
		if(airportDTO !=null) {
			return ResponseEntity.ok(airportDTO);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public AirportDTO createAirport(@RequestBody AirportDTO airportDTO) {
		airports.put(airportDTO.getId(), airportDTO);
		return airportDTO;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AirportDTO> modifyAirport(@PathVariable Long id, @RequestBody AirportDTO airportDTO) {
		if(!airports.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		airportDTO.setId(id);
		airports.put(id, airportDTO);
		return ResponseEntity.ok(airportDTO);
	}
	
	@DeleteMapping("/{id}")
	public void deleteAirport(@PathVariable Long id) {
		airports.remove(id);
	}
	
	
	
	
}
