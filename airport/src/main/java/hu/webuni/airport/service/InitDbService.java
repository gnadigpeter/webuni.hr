package hu.webuni.airport.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.webuni.airport.model.AirportUser;
import hu.webuni.airport.repository.AirportUserRepository;

@Service
public class InitDbService {
	
	@Autowired
	AirportUserRepository airportUserRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public void createUsersIfNeeded() {
		if(!airportUserRepository.existsById("admin")) {
			airportUserRepository.save(new AirportUser("admin",passwordEncoder.encode("pass"), Set.of("admin", "user")));
		}
		
		if(!airportUserRepository.existsById("user")) {
			airportUserRepository.save(new AirportUser("user",passwordEncoder.encode("pass"), Set.of("user")));
		}
	}
}
