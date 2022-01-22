package hu.webuni.gnadigpeti.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.gnadigpeti.model.Company;
import hu.webuni.gnadigpeti.repository.CompanyRepository;

@Service
public class CompanyService {
//	private Map<Long, Company> companies = new HashMap<Long, Company>();
//	{
//		List<Employee> emp = new ArrayList<>();
//		emp.add(new Employee(1L, "aaa", "Jani", 0, null));
//		emp.add(new Employee(2L, "bbb", "Jani", 0, null));
//		emp.add(new Employee(3L, "ccc", "Jani", 0, null));
//		companies.put(1L, new Company(1L, "123", "test cp", emp));
//	}
	
	@Autowired
	CompanyRepository companyRepository;
	
	public List<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public Optional<Company> findById(long id) {
		return companyRepository.findById(id);
	}
	
	@Transactional
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	@Transactional
	public Company update(Company company) {
		if(companyRepository.existsById(company.getId())) {
			return companyRepository.save(company);
		}else {
			throw new NoSuchElementException();
		}
	}
	
	@Transactional
	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
}
