package hu.webuni.gnadigpeti.security;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.HrUser;
import hu.webuni.gnadigpeti.repository.EmployeeRepository;

@Service
public class HrUserDetailsService implements UserDetailsService {

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username)
									.orElseThrow(()-> new UsernameNotFoundException(username));
		
		return new HrUser(username, 
				employee.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority("USER")),
				employee);
//				airportUser.getRoles()
//					.stream()
//					.map(SimpleGrantedAuthority::new)
//					.collect(Collectors.toList()));
	}

}
