package hu.webuni.gnadigpeti.web;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		Employee employee = employeeService.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return employeeMapper.employeeToDTO(employee);
	}
	

	@PostMapping
	public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDTO));
		return employeeMapper.employeeToDTO(employee);
	}
	
	@PutMapping("/{id}")
	public EmployeeDTO modifyEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDTO){
		Employee employee = employeeMapper.dtoToEmployee(employeeDTO);
		employee.setId(id);
		try {
			EmployeeDTO saveEmployeeDTO = employeeMapper.employeeToDTO(employeeService.update(employee));
			return saveEmployeeDTO;
		}catch(NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.delete(id);
	}
	
	@GetMapping("/rank/{rank}")
	public List<EmployeeDTO> getByRank(@PathVariable String rank){
		return employeeMapper.employeesToDTOs(employeeService.findByRank(rank));
	}
	@GetMapping("/name/{name}")
	public List<EmployeeDTO> getByName(@PathVariable String name){
		return employeeMapper.employeesToDTOs(employeeService.findByNameStartingWith(name));
	}
	@GetMapping("/startdate/{date1}/{date2}")
	public List<EmployeeDTO> getByStartDateBetween(@PathVariable String date1, @PathVariable String date2){//
		return employeeMapper.employeesToDTOs(employeeService.findByStartDateBetween(date1, date2));
	}
}
