package hu.webuni.airport.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.airport.model.Flight;


public class FlightSpecifications {
	public static Specification<Flight> hasId(long id){
		//return (root, cq, cb) -> cb.equal(root.get(Flight_.id), id);
		return (root, cq, cb) -> cb.equal(root.get("id"), id);
	}

	public static Specification<Flight> hasFlightNumber(String flightNumber) {
		return (root, cq, cb) -> cb.like(root.get("flightNumber"), flightNumber+"%");
	}
	
	public static Specification<Flight> hasTakeoffTime(LocalDateTime takeoffTime) {
		LocalDateTime startOfDay = LocalDateTime.of(takeoffTime.toLocalDate(), LocalTime.of(0, 0));
		return (root, cq, cb) -> cb.between(root.get("takeoffTime"), 
				startOfDay, startOfDay.plusDays(1));
	}
	public static Specification<Flight> hasTakeoffIata(String takeoffIata) {
		return (root, cq, cb) -> cb.like(root.get("takeoff").get("iata"),
				takeoffIata+"%");
	}
}
