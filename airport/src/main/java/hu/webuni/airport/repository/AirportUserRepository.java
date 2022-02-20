package hu.webuni.airport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hu.webuni.airport.model.AirportUser;

public interface AirportUserRepository extends JpaRepository<AirportUser, String>{

	boolean existsById(String string);

	Optional<AirportUser> findById(String username);

}
