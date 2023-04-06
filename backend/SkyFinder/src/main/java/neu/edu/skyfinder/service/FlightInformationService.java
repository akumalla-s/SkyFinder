package neu.edu.skyfinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import neu.edu.skyfinder.entity.FlightInformation;

@Service
public class FlightInformationService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<FlightInformation> displaySkywaveFlights() throws JsonMappingException, JsonProcessingException {
		String url = "http://localhost:8081/displayAllFlights";
		String jsonResponse = restTemplate.getForObject(url, String.class);
		//System.out.println(jsonResponse);
		ObjectMapper mapper = new ObjectMapper();
		List<FlightInformation> flightList = mapper.readValue(jsonResponse, new TypeReference<List<FlightInformation>>(){});
		
//		for(int i=0; i<flightList.size();i++) {
//			FlightInformation flightInformation = flightList.get(i);
//			System.out.println(flightInformation.getFlightNumber());
//			
//		}
		return flightList;
	}

}