package hu.webuni.airport.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.airport.model.AirportUser;
import hu.webuni.airport.repository.AirportUserRepository;

@Service
public class AirportUserDetailsService implements UserDetailsService {

	
	@Autowired
	AirportUserRepository airportUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AirportUser airportUser = airportUserRepository.findById(username)
									.orElseThrow(()-> new UsernameNotFoundException(username));
		return new User(username, airportUser.getPassword(),
				airportUser.getRoles()
					.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()));
	}

}
