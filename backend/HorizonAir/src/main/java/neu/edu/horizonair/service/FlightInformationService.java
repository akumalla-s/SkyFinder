package neu.edu.horizonair.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.horizonair.controller.FlightInformationModel;
import neu.edu.horizonair.controller.SearchFieldsModel;
import neu.edu.horizonair.entity.FlightInformation;
import neu.edu.horizonair.repository.FlightInformationRepository;

@Service
public class FlightInformationService {
	
	@Autowired
	private FlightInformationRepository repository;
	
	public List<FlightInformation> displayAllFlights() {
		return repository.findAll();
	}

	public List<FlightInformation> displayBasedOnSearch(SearchFieldsModel searchFieldsModel) throws ParseException {
		String origin = searchFieldsModel.getOrigin();
		String destination = searchFieldsModel.getDestination();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchFieldsModel.getDate());
		
		List<FlightInformation> flightList = repository.findByOriginAndDestinationAndStartDate(origin,destination,startDate);
        
		return flightList;
	}
	
	public FlightInformation displayFlight(String flightnumber) {
		Optional<FlightInformation> flightInformation = repository.findById(flightnumber);
		if(flightInformation.isPresent()) {
			FlightInformation flight = flightInformation.get();
			Integer availableSeats = Integer.parseInt(flight.getAvailableSeats());
			availableSeats = availableSeats - 1;
			String updatedSeats = availableSeats.toString();
			flight.setAvailableSeats(updatedSeats);
			flight = repository.save(flight);
			return flight;
			
		}
		System.out.println("Error in Flight Information Service");
		
		return null;
	}
	
	public FlightInformation createNewFlight(FlightInformationModel flightInformationModel) {
		FlightInformation flightInformation = new FlightInformation();
		flightInformation.setFlightNumber(flightInformationModel.getFlightNumber());
		flightInformation.setStartDate(flightInformationModel.getStartDate());
		flightInformation.setOrigin(flightInformationModel.getOrigin());
		flightInformation.setDestination(flightInformationModel.getDestination());
		flightInformation.setDepartureTime(flightInformationModel.getDepartureTime());
		flightInformation.setArrivalTime(flightInformationModel.getArrivalTime());
		flightInformation.setDuration(flightInformationModel.getDuration());
		flightInformation.setPrice(flightInformationModel.getPrice());
		flightInformation.setCapacity(flightInformationModel.getCapacity());
		flightInformation.setAvailableSeats(flightInformationModel.getAvailableSeats());
		flightInformation.setAirline(flightInformationModel.getAirline());
		flightInformation.setStatus(flightInformationModel.getStatus());
		flightInformation.setUserRatings(flightInformationModel.getUserRatings());
		
		repository.saveAndFlush(flightInformation);
		return flightInformation;
	}

	public boolean deleteFlight(String flightNumber) {
		Optional<FlightInformation> flightInformation = repository.findById(flightNumber);
		if(flightInformation.isPresent()) {
			repository.deleteById(flightNumber);
			return true;
		}
		return false;
	}

	
	public FlightInformation displayFlightById(String flightNumber) {
		Optional<FlightInformation> flightInformation = repository.findById(flightNumber);
		if(flightInformation.isPresent()) {
			FlightInformation flight = flightInformation.get();
			return flight;
			
		}
		System.out.println("Error in Flight Information Service - displayFlightById");		
		return null;
	}

}
