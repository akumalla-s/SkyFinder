package neu.edu.skyfinder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import neu.edu.skyfinder.controller.model.FlightBookingModel;
import neu.edu.skyfinder.controller.model.SearchFieldsModel;
import neu.edu.skyfinder.email.FlightBookingEmailSenderService;
import neu.edu.skyfinder.email.FlightCancelEmailSenderService;
import neu.edu.skyfinder.entity.FlightBooking;
import neu.edu.skyfinder.entity.FlightInformation;
import neu.edu.skyfinder.entity.User;
import neu.edu.skyfinder.repository.FlightBookingRepository;
import neu.edu.skyfinder.repository.UserRepository;

@Service
public class FlightInformationService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FlightComparator flightComparator;

	@Autowired
	private FlightBookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightBookingEmailSenderService emailSenderService;

	@Autowired
	private FlightCancelEmailSenderService flightCancelEmailSenderService;

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

	public List<FlightInformation> displayFlightsBasedOnInput(SearchFieldsModel model)
			throws JsonMappingException, JsonProcessingException {
		String url1 = "http://localhost:8081/displayBasedOnSearch";
		String url2 = "http://localhost:8082/displayBasedOnSearch";
		String url3 = "http://localhost:8083/displayBasedOnSearch";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("origin", model.getOrigin());
		requestBody.put("destination", model.getDestination());
		requestBody.put("date", model.getDate());

		HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

		try {
			// send request and retrieve response
			ResponseEntity<List<FlightInformation>> response1 = restTemplate.exchange(url1, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<FlightInformation>>() {
					});
			ResponseEntity<List<FlightInformation>> response2 = restTemplate.exchange(url2, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<FlightInformation>>() {
					});
			ResponseEntity<List<FlightInformation>> response3 = restTemplate.exchange(url3, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<FlightInformation>>() {
					});
			// extract response body
			List<FlightInformation> flightList1 = response1.getBody();
			List<FlightInformation> flightList2 = response2.getBody();
			List<FlightInformation> flightList3 = response3.getBody();

			List<FlightInformation> flightList = combineMyLists(flightList1, flightList2, flightList3);

			List<FlightInformation> aggregateData = flightComparator.aggregateBasedOnOverallScore(flightList);

			return aggregateData;

		} catch (Exception e) {
			System.out.println("Error in Flight Information Service");
			return null;
		}

	}

	@SafeVarargs
	private List<FlightInformation> combineMyLists(List<FlightInformation>... args) {
		List<FlightInformation> combinedList = Stream.of(args).flatMap(i -> i.stream()).collect(Collectors.toList());
		;
		return combinedList;
	}

	public FlightInformation bookFlight(FlightBookingModel flightBookingModel) {
		String url = null;
		if (flightBookingModel.getFlightNumber().contains("SW")) {
			url = "http://localhost:8081/bookFlight/" + flightBookingModel.getFlightNumber();
		} else if (flightBookingModel.getFlightNumber().contains("JS")) {
			url = "http://localhost:8082/bookFlight/" + flightBookingModel.getFlightNumber();
		} else if (flightBookingModel.getFlightNumber().contains("HA")) {
			url = "http://localhost:8083/bookFlight/" + flightBookingModel.getFlightNumber();
		}

		FlightBooking flightBooking = new FlightBooking();
		FlightInformation flightInformation = restTemplate.getForObject(url, FlightInformation.class);
		if (flightInformation.getFlightNumber().equals(flightBookingModel.getFlightNumber())) {
			flightBooking.setUsername(flightBookingModel.getUsername());
			flightBooking.setFlightNumber(flightInformation.getFlightNumber());
			flightBooking.setOrigin(flightInformation.getOrigin());
			flightBooking.setDestination(flightInformation.getDestination());
			flightBooking.setDepartureTime(flightInformation.getDepartureTime());
			flightBooking.setArrivalTime(flightInformation.getArrivalTime());
			flightBooking.setDuration(flightInformation.getDuration());
			flightBooking.setPrice(flightInformation.getPrice());
			flightBooking.setAirline(flightInformation.getAirline());
			flightBooking.setStatus(flightInformation.getStatus());
			bookingRepository.saveAndFlush(flightBooking);
		}
		String username = flightBookingModel.getUsername();

		Optional<User> user = userRepository.findById(username);

		try {
			emailSenderService.sendEmailDetails(user, flightInformation);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FlighInformationService - bookFlight method");
			return flightInformation;
		}

		return flightInformation;

	}

	public List<FlightBooking> displayUserBookings(String username) {
		List<FlightBooking> flightBooking = bookingRepository.findByUsername(username);
		return flightBooking;
	}

	public boolean cancelFlightBooking(int bookingid) {
		Optional<FlightBooking> flightBooking = bookingRepository.findById(bookingid);
		if (flightBooking.isPresent()) {
			FlightBooking booking = flightBooking.get();
			Optional<User> user = userRepository.findById(booking.getUsername());
			User user2 = user.get();
			try {
				flightCancelEmailSenderService.sendEmailDetails(user2, booking);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Flight Information Service - cancelFlightBooking");
			}
			
			try {
				String url = null;
				if (booking.getFlightNumber().contains("SW")) {
					url = "http://localhost:8081/updateFlight/" + booking.getFlightNumber();
				} else if (booking.getFlightNumber().contains("JS")) {
					url = "http://localhost:8082/updateFlight/" + booking.getFlightNumber();
				} else if (booking.getFlightNumber().contains("HA")) {
					url = "http://localhost:8083/updateFlight/" + booking.getFlightNumber();
				}
				restTemplate.put(url, FlightInformation.class);
				
			} catch (Exception e) {
				System.out.println("Flight Information Service - cancelFlightBooking - Couldn't update count");
			}
			

		}

		boolean isDeleted = false;
		try {
			bookingRepository.deleteById(bookingid);
			isDeleted = true;
		} catch (Exception e) {
			return false;
		}
		return isDeleted;

	}

	public List<FlightBooking> displayBookings() {
		List<FlightBooking> flightBooking = bookingRepository.findAll();
		return flightBooking;
	}

}
