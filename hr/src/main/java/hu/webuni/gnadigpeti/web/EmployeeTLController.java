package hu.webuni.gnadigpeti.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.model.Employee;

@Controller
public class EmployeeTLController {
	
	private List<Employee> allEmployees = new ArrayList<Employee>();
	
	{
		allEmployees.add(new Employee(1L, "Sebastian Silverstone", "brewer", 3000, LocalDateTime.of(2018, 12, 11, 12, 00)));
		allEmployees.add(new Employee(2L, "Leviticus", "actor", 1500, LocalDateTime.of(2018, 12, 11, 12, 00)));
		allEmployees.add(new Employee(3L, "Remi Malcor", "botanist", 4500, LocalDateTime.of(2018, 12, 11, 12, 00)));
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Map<String, Object> model) {
		model.put("employees", allEmployees);
		model.put("newEmployee", new Employee());
		
		System.out.println("-------");
		for(Employee emp: allEmployees) {
			System.out.println(emp.toString());
		}
		
		return "/employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";
	}
	
}
