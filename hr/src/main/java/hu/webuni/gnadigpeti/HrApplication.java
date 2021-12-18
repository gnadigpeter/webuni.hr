package hu.webuni.gnadigpeti;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.gnadigpeti.config.HrConfigProperties;
import hu.webuni.gnadigpeti.config.HrConfigProperties.Smart;
import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;
	@Autowired
	HrConfigProperties config;
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Smart smartConfig = config.getSalary().getSmart();
		for (Double limit : smartConfig.getLimits().keySet()) {
			
			int origSalary = 100;
			LocalDateTime limitDay = LocalDateTime.now().minusDays((long)(limit*365));
			Employee e1 = new Employee(1L, "Nagy Péter", "fejlesztő", origSalary, limitDay.plusDays(1));
			Employee e2 = new Employee(2L, "Kis Gábor", "projektmenedzser", origSalary, limitDay.minusDays(1));

			salaryService.setEmployeeSalery(e1);
			salaryService.setEmployeeSalery(e2);

			System.out.format("1 nappal a %.2f éves határ előtt az új fizetés %d%n", limit, e1.getSalary());
			System.out.format("1 nappal a %.2f éves határ után az új fizetés %d%n", limit, e2.getSalary());
		}
		
		/*
		System.out.println("test");
		Employee employeeA = new Employee(1L, "name", "rank", 100, LocalDateTime.of(2018, 12, 04, 12, 00));
		System.out.println(employeeA.getSalary());
		salaryService.setEmployeeSalery(employeeA);
		System.out.println(employeeA.getSalary());
		
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		//System.out.println(dateTime);
		Employee employeeB = new Employee(2L, "name", "rank", 500, "2016-03-04 11:30");
		System.out.println(employeeB.getSalary());
		salaryService.setEmployeeSalery(employeeB);
		System.out.println(employeeB.getSalary());
		*/
	}

}
