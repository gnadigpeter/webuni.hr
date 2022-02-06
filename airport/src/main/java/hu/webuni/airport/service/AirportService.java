package hu.webuni.airport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.airport.model.Airport;
import hu.webuni.airport.model.Flight;
import hu.webuni.airport.repository.AirportRepository;
import hu.webuni.airport.repository.FlightRepository;


@Service
public class AirportService {
//	private Map<Long, Airport> airports  = new HashMap<>();
//	
//	{
//		airports.put(1L, new Airport(1, "abc","XYZ"));
//		airports.put(2L, new Airport(2, "def","UVW"));
//	}
	
//	@PersistenceContext
//	EntityManager em;
	
	AirportRepository airportRepository;
	FlightRepository flightRepository;
	


	public AirportService(AirportRepository airportRepository, FlightRepository flightRepository) {
		super();
		this.airportRepository = airportRepository;
		this.flightRepository = flightRepository;
	}

	@Transactional
	public Airport save(Airport airport) {
		checkUniqueIata(airport.getIata(), null);
//		em.persist(airport);
		return airportRepository.save(airport);
	}
	
	@Transactional
	public Airport update(Airport airport) {
		checkUniqueIata(airport.getIata(), airport.getId());
		if(airportRepository.existsById(airport.getId()))
			return airportRepository.save(airport);
		else
			throw new NoSuchElementException();
	}
	
	private void checkUniqueIata(String iata, Long id) {
		
		boolean forUpdate = id != null;
//		TypedQuery<Long> querry = em.createNamedQuery(forUpdate ? "Airport.countByIataAndIdNotIn" : "Airport.countByIata", Long.class)
//		.setParameter("iata", iata);
//		if(forUpdate) {
//			querry.setParameter("id", id);
//		}
//		Long count = querry
//		.getSingleResult();
		
		Long count = forUpdate ? 
				airportRepository.countByIataAndIdNot(iata, id)
				: airportRepository.countByIata(iata);
		if(count > 0) {
			throw new NonUniqueIataException(iata);
		}
}
	
	public List<Airport> findAll(){
//		return em.createQuery("SELECT a FROM Airport a", Airport.class).getResultList();
		return airportRepository.findAll();
	}
	
	public Optional<Airport> findById(long id) {
//		return em.find(Airport.class, id);
		return airportRepository.findById(id);
	}
	
	@Transactional
	public void delete(long id) {
//		em.remove(findById(id));
		airportRepository.deleteById(id);
	}
	
	@Transactional
	public Flight createFlight(String flightNumber, long takeOfAirportId, long landingAirportId, LocalDateTime takeOfDateTime) {
		Flight flight = new Flight();
		flight.setFlightNumber(flightNumber);
		flight.setTakeoff(airportRepository.findById(takeOfAirportId).get());
		flight.setLanding(airportRepository.findById(landingAirportId).get());
		flight.setTakeoffTime(takeOfDateTime);
		return flightRepository.save(flight);
	}
	
	public List<Flight> findFlightsByExample(Flight example){
		Long id = example.getId();
		String flightNumber = example.getFlightNumber();	
		String takeoffIata = null;
		Airport takeoff = example.getTakeoff();
		if(takeoff != null) {
			takeoffIata = takeoff.getIata();
		}
		LocalDateTime takeoffTime = example.getTakeoffTime();
		
		Specification<Flight> spec = Specification.where(null);
		
		if (id != null && id > 0) {
			spec = spec.and(FlightSpecifications.hasId(id));
		}
		
		if(StringUtils.hasText(flightNumber)) {
			spec = spec.and(FlightSpecifications.hasFlightNumber(flightNumber));
		}
		
		if(StringUtils.hasText(takeoffIata)) {
			spec = spec.and(FlightSpecifications.hasTakeoffIata(takeoffIata));
		}
		
		if(takeoffTime !=null) {
			spec = spec.and(FlightSpecifications.hasTakeoffTime(takeoffTime));
		}

		return flightRepository.findAll(spec, Sort.by("id"));
	}
}
