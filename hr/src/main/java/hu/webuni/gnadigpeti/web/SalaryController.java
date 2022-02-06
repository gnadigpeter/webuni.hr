package hu.webuni.gnadigpeti.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.gnadigpeti.service.SalaryService;

@RequestMapping("/api/salary")
@RestController
public class SalaryController {

	@Autowired
	SalaryService salaryService;
	
	
	//@PutMapping("/{positionName}/raiseMin/{minSalary}")
	@PutMapping("/{companyId}/{positionName}/raiseMin/{minSalary}")
	public void raiseMinSalary(@PathVariable long companyId, @PathVariable String positionName, @PathVariable int minSalary) {
		salaryService.raiseMinSalary(companyId, positionName , minSalary);
	}
}