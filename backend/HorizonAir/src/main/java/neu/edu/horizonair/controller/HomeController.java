package neu.edu.horizonair.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public FlightInformation bookFlight(@PathVariable String flightnumber){
    	FlightInformation flightInformation = service.displayFlight(flightnumber);
		return flightInformation;    	
    }
	
	@PutMapping("/updateFlight/{flightnumber}")
	public FlightInformation updateFlight(@PathVariable String flightnumber) {
		FlightInformation flightInformation = service.updateFlight(flightnumber);
		return flightInformation;
	}
	
	@PostMapping("/createNewFlight")
	public ResponseEntity<FlightInformation> createNewFlight(@RequestBody FlightInformationModel flightInformationModel){
		FlightInformation flightInformation = service.createNewFlight(flightInformationModel);
		return new ResponseEntity<>(flightInformation, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteFlight/{flightNumber}")
	public boolean deleteFlight(@PathVariable String flightNumber) {
		return service.deleteFlight(flightNumber); 
	}
	
	@GetMapping("/displayFlightById/{flightNumber}")
	public ResponseEntity<FlightInformation> displayFlightById(@PathVariable String flightNumber){
		FlightInformation flightInformation=service.displayFlightById(flightNumber);
		return new ResponseEntity<>(flightInformation,HttpStatus.OK);
	}
}
