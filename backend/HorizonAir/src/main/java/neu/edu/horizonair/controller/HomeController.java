package neu.edu.horizonair.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import neu.edu.horizonair.entity.FlightInformation;
import neu.edu.horizonair.service.FlightInformationService;


@RestController
public class HomeController {
	
	@Autowired
	FlightInformationService service;

	@RequestMapping("/")
	public String TestApp() {
		return "Hello from Horizon Air";
	}
	
	@GetMapping("/displayAllFlights")
    public List<FlightInformation> displayAllFlights(){
    	return service.displayAllFlights();
    }
	
	@RequestMapping("/displayBasedOnSearch")
    public ResponseEntity<List<FlightInformation>> displayBasedOnSearch(@RequestBody SearchFieldsModel searchFieldsModel) throws ParseException {
    	List<FlightInformation> flightInformation = service.displayBasedOnSearch(searchFieldsModel);
    	return new ResponseEntity<List<FlightInformation>>(flightInformation, HttpStatus.OK); 
    }
	
	@GetMapping("/bookFlight/{flightnumber}")
    public FlightInformation displayFlightInformation(@PathVariable String flightnumber){
    	FlightInformation flightInformation = service.displayFlight(flightnumber);
		return flightInformation;
    	
    }
}
