package neu.edu.jetstream.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.jetstream.controller.SearchFieldsModel;
import neu.edu.jetstream.entity.FlightInformation;
import neu.edu.jetstream.repository.FlightInformationRepository;


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

}
