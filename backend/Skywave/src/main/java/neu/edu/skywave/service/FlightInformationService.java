package neu.edu.skywave.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.skywave.controller.SearchFieldsModel;
import neu.edu.skywave.entity.FlightInformation;
import neu.edu.skywave.repository.FlightInformationRepository;

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

}
