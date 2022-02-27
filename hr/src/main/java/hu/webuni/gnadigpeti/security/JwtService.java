package hu.webuni.gnadigpeti.security;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.gnadigpeti.config.HrConfigProperties;
import hu.webuni.gnadigpeti.config.HrConfigProperties.JwtData;
import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.HrUser;

@Service
public class JwtService {

	private static final String MANAGED_EMPLOYEE_USERNAMES = "managedEmployeeUsernames";
	private static final String MANAGED_EMPLOYEE_IDS = "managedEmployeeIds";
	private static final String USERNAME = "username";
	private static final String MANAGER = "manager";
	private static final String ID = "id";
	private static final String FULLNAME = "fullname";
	private static final String AUTH = "auth";
	private Algorithm alg;
	private String issuer;
	
	@Autowired
	private HrConfigProperties hrConfigProperties;
	
	@PostConstruct
	public void init() {
		JwtData jwtData = hrConfigProperties.getJwtData();
		issuer = jwtData.getIssuer();
		try {
			alg = (Algorithm) Algorithm.class.getMethod(jwtData.getAlg(), String.class)
					.invoke(Algorithm.class, jwtData.getSecret());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public String createJwtToken(UserDetails principal) {
		Employee employee = ((HrUser) principal).getEmployee();
		Employee manager = employee.getManager();
		List<Employee> managedEmployee = employee.getManagedEmployee();
		
		Builder jwtBuilder = JWT.create()
			.withSubject(principal.getUsername())
			.withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
			.withClaim(FULLNAME, employee.getName())
			.withClaim(ID, employee.getId());
		
		if(manager != null) {
			jwtBuilder
				.withClaim(MANAGER, Map.of(
						ID, manager.getId(),
						USERNAME, manager.getUsername()
						));
		}
		
		if(managedEmployee != null && !managedEmployee.isEmpty()) {
			jwtBuilder
				.withArrayClaim(MANAGED_EMPLOYEE_IDS, 
						managedEmployee.stream().map(Employee::getId).toArray(Long[]::new)
						)
				.withArrayClaim(MANAGED_EMPLOYEE_USERNAMES, 
						managedEmployee.stream().map(Employee::getUsername).toArray(String[]::new)
						);
		}
		
		return jwtBuilder
			.withExpiresAt(new Date(System.currentTimeMillis() + hrConfigProperties.getJwtData().getDureation().toMillis()))
			.withIssuer(issuer)
			.sign(alg);
	}

	public UserDetails parseJwt(String jwtToken) {
		
		DecodedJWT decodedJwt = JWT.require(alg)
			.withIssuer(issuer)
			.build()
			.verify(jwtToken);
		
		
		Employee employee = new Employee();
		employee.setId(decodedJwt.getClaim(ID).asLong());
		employee.setUsername(decodedJwt.getSubject());
		employee.setName(decodedJwt.getClaim(FULLNAME).asString());
		
		Claim managerClaim = decodedJwt.getClaim(MANAGER);
		if(managerClaim != null) {
			Employee manager = new Employee();
			employee.setManager(manager);
			Map<String, Object> managerData = managerClaim.asMap();
			if(managerData != null) {
				manager.setId(((Integer)managerData.get(ID)).longValue());
				manager.setUsername((String) managerData.get(USERNAME));
			}
		}
		
		Claim managedEmployeeUsernamesClaim = decodedJwt.getClaim(MANAGED_EMPLOYEE_USERNAMES);
		if(managedEmployeeUsernamesClaim != null) {
			employee.setManagedEmployee(new ArrayList<>());
			List<String> managedEmployeeUsernames = managedEmployeeUsernamesClaim.asList(String.class);
			if(managedEmployeeUsernames != null && !managedEmployeeUsernames.isEmpty()) {
				List<Long> managedEmployeeIds = decodedJwt.getClaim(MANAGED_EMPLOYEE_IDS).asList(Long.class);
				for (int i = 0; i < managedEmployeeUsernames.size(); i++) {
					Employee managedEmloyee = new Employee();
					managedEmloyee.setId(managedEmployeeIds.get(i));
					managedEmloyee.setUsername(managedEmployeeUsernames.get(i));
					employee.getManagedEmployee().add(managedEmloyee);
				}
			}
		}
		
		return new HrUser(decodedJwt.getSubject(), "dummy",
				decodedJwt.getClaim(AUTH).asList(String.class)
					.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()),
				employee);
		
//		return new User(decodedJwt.getSubject(),"dummy",
//				decodedJwt.getClaim(AUTH).asList(String.class)
//					.stream()
//					.map(SimpleGrantedAuthority::new)
//					.collect(Collectors.toList())
//				);
//		
	}

}
