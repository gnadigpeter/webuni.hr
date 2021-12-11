package hu.webuni.gnadigpeti;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("test");
		
		
		Employee employeeA = new Employee(1L, "name", "rank", 100, LocalDateTime.of(2018, 12, 04, 12, 00));
		
		System.out.println(employeeA.getStartDate());
		
		System.out.println(employeeA.getSalary());
		salaryService.setEmployeeSalery(employeeA);
		System.out.println(employeeA.getSalary());
		
		
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		//System.out.println(dateTime);
		
		Employee employeeB = new Employee(2L, "name", "rank", 500, "2016-03-04 11:30");
		System.out.println(employeeB.getStartDate());
	}

}
