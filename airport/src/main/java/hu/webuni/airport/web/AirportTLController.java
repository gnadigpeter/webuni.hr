package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.airport.dto.AirportDTO;

@Controller
public class AirportTLController {


	private List<AirportDTO> allAirports = new ArrayList<AirportDTO>();
	
	{
		allAirports.add(new AirportDTO(1, "Ferenc List Airport", "BUD"));
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/airports")
	public String listAirports(Map<String, Object> model) {
		model.put("airports", allAirports);
		model.put("newAirport", new AirportDTO());
		return "/airports";
	}
	
	@PostMapping("/airports")
	public String addAirport(AirportDTO airportDTO) {
		allAirports.add(airportDTO);
		return "redirect:airports";
	}
	
	
	
	
}
