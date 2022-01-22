package hu.webuni.gnadigpeti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.gnadigpeti.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
