package hu.webuni.gnadigpeti.web;

import java.time.LocalDateTime;
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

import hu.webuni.gnadigpeti.dto.EmployeeDTO;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private Map<Long, EmployeeDTO> employees = new HashMap<Long, EmployeeDTO>();
	
	{
		employees.put(1L, new EmployeeDTO(1L, "Sebastian Silverstone", "brewer", 3000, LocalDateTime.of(2018, 12, 11, 12, 00)));
		employees.put(2L, new EmployeeDTO(2L, "Leviticus", "actor", 1500, LocalDateTime.of(2018, 12, 11, 12, 00)));
		employees.put(3L, new EmployeeDTO(3L, "Remi Malcor", "botanist", 4500, LocalDateTime.of(2018, 12, 11, 12, 00)));
	}
	
	@GetMapping()
	public List<EmployeeDTO> getAll(){
		return new ArrayList<>(employees.values());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id){
		EmployeeDTO employeeDTO = employees.get(id);
		if(employeeDTO != null) {
			return ResponseEntity.ok(employeeDTO);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/salery/{value}")
	public List<EmployeeDTO> getBySaleryBiggerThen(@PathVariable Long value){
		ArrayList<EmployeeDTO> all = new ArrayList<>(employees.values());
		ArrayList<EmployeeDTO> response = new ArrayList<>();
		
		for(EmployeeDTO employeeDTO: all) {
			if(employeeDTO.getSalary() > value) {
				response.add(employeeDTO);
			}
		}
		return response;
	}
	
	@PostMapping
	public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		employees.put(employeeDTO.getId(), employeeDTO);
		return employeeDTO;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDTO> modifyEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
		if(!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		employeeDTO.setId(id);
		employees.put(id, employeeDTO);
		return ResponseEntity.ok(employeeDTO);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employees.remove(id);
	}
	
	
	
	
	
	
	
	
	
	
	
}
