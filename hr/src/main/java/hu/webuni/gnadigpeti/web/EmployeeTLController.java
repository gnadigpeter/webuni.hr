package hu.webuni.gnadigpeti.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.gnadigpeti.dto.EmployeeDTO;
import hu.webuni.gnadigpeti.model.Employee;

@Controller
public class EmployeeTLController {
	
	private List<Employee> allEmployees = new ArrayList<Employee>();
	
//	{
//		allEmployees.add(new Employee(1L, "Sebastian Silverstone", "brewer", 3000, LocalDateTime.of(2018, 12, 11, 12, 00)));
//		allEmployees.add(new Employee(2L, "Leviticus", "actor", 1500, LocalDateTime.of(2018, 12, 11, 12, 00)));
//		allEmployees.add(new Employee(3L, "Remi Malcor", "botanist", 4500, LocalDateTime.of(2018, 12, 11, 12, 00)));
//	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Map<String, Object> model) {
		model.put("employees", allEmployees);
		model.put("newEmployee", new Employee());
		
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
	
	@GetMapping("deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable long id) {
		allEmployees.removeIf(emp -> emp.getId() == id);
		return "redirect:/employees";
	}
	
	@GetMapping("/employees/{id}")
	public String editEmployee(@PathVariable long id, Map<String, Object> model) {
		model.put("employee", allEmployees.stream()
				.filter(e -> e.getId() == id)
				.findFirst()
				.get());
		return "editEmployee";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(Employee employee) {
		for(int i=0; i < allEmployees.size(); i++) {
			if(allEmployees.get(i).getId() == employee.getId()) {
				allEmployees.set(i, employee);
				break;
			}
		}
		return "redirect:employees";
	}
	
	
	
}
