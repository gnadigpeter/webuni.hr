package hu.webuni.gnadigpeti;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
		System.out.println(employeeA.getSalary());
		salaryService.setEmployeeSalery(employeeA);
		System.out.println(employeeA.getSalary());
	}

}
