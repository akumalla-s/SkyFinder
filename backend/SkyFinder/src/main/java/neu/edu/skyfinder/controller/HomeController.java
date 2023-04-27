package neu.edu.skyfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import neu.edu.skyfinder.controller.model.FlightBookingModel;
import neu.edu.skyfinder.controller.model.SearchFieldsModel;
import neu.edu.skyfinder.entity.FlightBooking;
import neu.edu.skyfinder.entity.FlightInformation;
import neu.edu.skyfinder.service.FlightInformationService;

@RestController
@CrossOrigin("http://localhost:3000")
public class HomeController {
	
	@Autowired
	FlightInformationService service;

	@GetMapping("/displaySkywaveFlights")
    public ResponseEntity<List<FlightInformation>> displaySkywaveFlights() throws JsonMappingException, JsonProcessingException{
		List<FlightInformation> flightInformation = service.displaySkywaveFlights();
		return new ResponseEntity<List<FlightInformation>>(flightInformation, HttpStatus.OK);
    }
	
	@RequestMapping("/displayFlightsBasedOnInput")
	public ResponseEntity<List<FlightInformation>> displayFlightsBasedOnInput(@RequestBody SearchFieldsModel searchFieldsModel) throws JsonMappingException, JsonProcessingException{
		List<FlightInformation> flightInformation = service.displayFlightsBasedOnInput(searchFieldsModel);
		return new ResponseEntity<List<FlightInformation>>(flightInformation, HttpStatus.OK);
	}
	
	@PostMapping("/bookFlight")
	public ResponseEntity<FlightInformation> bookFlight(@RequestBody FlightBookingModel flightBookingModel){
		FlightInformation flightInformation = service.bookFlight(flightBookingModel);
		return new ResponseEntity<FlightInformation>(flightInformation, HttpStatus.OK);
	}
	
	@GetMapping("displayUserBookings/{username}")
	public ResponseEntity<List<FlightBooking>> displayUserBookings(@PathVariable String username) {
		List<FlightBooking> flightBooking = service.displayUserBookings(username);
		return new ResponseEntity<List<FlightBooking>>(flightBooking, HttpStatus.OK);
	}
	
	@GetMapping("/displayBookings")
	public ResponseEntity<List<FlightBooking>> displayBookings() {
		List<FlightBooking> flightBooking = service.displayBookings();
		return new ResponseEntity<List<FlightBooking>>(flightBooking, HttpStatus.OK);
	} 
	@DeleteMapping("/cancelFlightBooking/{bookingid}")
	public ResponseEntity<Boolean> cancelFlightBooking(@PathVariable int bookingid) {
		boolean isDeleted = service.cancelFlightBooking(bookingid);
		return new ResponseEntity<>(isDeleted, HttpStatus.OK);
	}
	
}
