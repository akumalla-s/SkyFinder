package neu.edu.skyfinder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import neu.edu.skyfinder.controller.SearchFieldsModel;
import neu.edu.skyfinder.entity.FlightInformation;

@Service
public class FlightInformationService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FlightComparator flightComparator;

	public List<FlightInformation> displaySkywaveFlights() throws JsonMappingException, JsonProcessingException {
		String url = "http://localhost:8081/displayAllFlights";
		String jsonResponse = restTemplate.getForObject(url, String.class);
		// System.out.println(jsonResponse);
		ObjectMapper mapper = new ObjectMapper();
		List<FlightInformation> flightList = mapper.readValue(jsonResponse,
				new TypeReference<List<FlightInformation>>() {
				});

//		for(int i=0; i<flightList.size();i++) {
//			FlightInformation flightInformation = flightList.get(i);
//			System.out.println(flightInformation.getFlightNumber());
//			
//		}
		return flightList;
	}

	public List<FlightInformation> displayFlightsBasedOnInput(SearchFieldsModel model) throws JsonMappingException, JsonProcessingException {
		String url = "http://localhost:8081/displayBasedOnSearch";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("origin", model.getOrigin());
		requestBody.put("destination", model.getDestination());
		requestBody.put("date", model.getDate());

		HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
		
		// send request and retrieve response
		ResponseEntity<List<FlightInformation>> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<List<FlightInformation>>() {});

		// extract response body
		List<FlightInformation> flightList = response.getBody();
		
		List<FlightInformation> aggregateData = flightComparator.aggregateBasedOnOverallScore(flightList);
		 
		return aggregateData;
		

	}

}
