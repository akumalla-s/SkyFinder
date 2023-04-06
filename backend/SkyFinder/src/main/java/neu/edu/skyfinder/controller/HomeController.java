package neu.edu.skyfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import neu.edu.skyfinder.entity.FlightInformation;
import neu.edu.skyfinder.service.FlightInformationService;

@RestController
public class HomeController {
	
	@Autowired
	FlightInformationService service;

	@GetMapping("/displaySkywaveFlights")
    public ResponseEntity<List<FlightInformation>> displaySkywaveFlights() throws JsonMappingException, JsonProcessingException{
		List<FlightInformation> flightInformation = service.displaySkywaveFlights();
		return new ResponseEntity<List<FlightInformation>>(flightInformation, HttpStatus.OK);
    }
}
