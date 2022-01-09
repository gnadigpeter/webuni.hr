package hu.webuni.gnadigpeti.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.mapper.EmployeeMapper;
import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.service.SmartEmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	SmartEmployeeService employeeService;
	
	@Autowired
	EmployeeMapper employeeMapper;

	
	
	@GetMapping()
	public List<EmployeeDTO> getAll( @RequestParam(required =false) Integer minSalary){
		if(minSalary !=null) {
			return employeeMapper.employeesToDTOs(employeeService.findAll()).stream()
					.filter(e->e.getSalary() > minSalary)
					.collect(Collectors.toList());
		}
		return employeeMapper.employeesToDTOs(employeeService.findAll());
	}
	
	@GetMapping("/{id}")
	public EmployeeDTO getById(@PathVariable Long id){
		Employee employee = employeeService.findById(id);
		if(employee != null) {
			return employeeMapper.employeeToDTO(employee);
		}else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	

	@PostMapping
	public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDTO));
		return employeeMapper.employeeToDTO(employee);
	}
	
	@PutMapping("/{id}")
	public EmployeeDTO modifyEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDTO){
		if(employeeService.findById(id) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDTO));
		return employeeMapper.employeeToDTO(employee);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.delete(id);
	}
	
	/*@GetMapping()
	public List<EmployeeDTO> getAll( @RequestParam(required =false) Integer minSalary){
		if(minSalary !=null) {
			return employees.values().stream()
					.filter(e->e.getSalary() > minSalary)
					.collect(Collectors.toList());
		}
		return new ArrayList<>(employees.values());
	}*/
	
	/*
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id){
		EmployeeDTO employeeDTO = employees.get(id);
		if(employeeDTO != null) {
			return ResponseEntity.ok(employeeDTO);
		}
		return ResponseEntity.notFound().build();
	}
	
//	//1.megoldás
//	@GetMapping(params = "minSalary")
//	public List<EmployeeDTO> findBySalary(@RequestParam int minSalary){
//		return employees.values().stream()
//				.filter(e->e.getSalary() > minSalary)
//				.collect(Collectors.toList());
//	}
	
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
	public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
		employees.put(employeeDTO.getId(), employeeDTO);
		return employeeDTO;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDTO> modifyEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDTO){
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
	
	*/
}
