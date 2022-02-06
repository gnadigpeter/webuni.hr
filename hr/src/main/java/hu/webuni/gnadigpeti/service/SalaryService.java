package hu.webuni.gnadigpeti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.PositionDetailsByCompany;
import hu.webuni.gnadigpeti.repository.CompanyRepository;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;
import hu.webuni.gnadigpeti.repository.PositionDetailsByCompanyRepository;
import hu.webuni.gnadigpeti.repository.PositionRepository;

@Service
public class SalaryService {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	PositionRepository positionRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
	

	public void setEmployeeSalery(Employee employee) {
		employee.setSalary(employee.getSalary() / 100 * (100+employeeService.getPayRaisePercent(employee)));
	}
	
	@Transactional
	public void raiseMinSalary(long companyId,String positionName, int minSalarz) {
		
		List<PositionDetailsByCompany> positionDetails = positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyId);
		positionDetails.forEach(pd -> pd.setMinSalary(minSalarz));
		positionDetailsByCompanyRepository.updateSalaries(companyId, positionName, minSalarz);
		
//		positionRepository.findByName(positionName)
//		.forEach(p -> {
//			p.setMinSalary(minSalarz);
//			p.getEmployees().forEach(e->{
//				if(e.getSalary()<minSalary)
//				e.setSalary(minSalarz);
//			});
//		});
	}
}
