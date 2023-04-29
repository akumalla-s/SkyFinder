package neu.edu.skyfinder.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import neu.edu.skyfinder.entity.FlightInformation;

@Service
public class FlightComparator {

	public List<FlightInformation> aggregateBasedOnOverallScore(List<FlightInformation> flightList) {
		List<FlightInformation> result = new ArrayList<>();

		// Calculate the score for each flight and store it in a map
		Map<FlightInformation, Double> scoreMap = new HashMap<>();
		for (FlightInformation flight : flightList) {
			double priceScore = 1 / Double.parseDouble(flight.getPrice());
			double durationScore = 1 / Double.parseDouble(flight.getDuration());
			double userRatingScore = Double.parseDouble(flight.getUserRatings());
			
			double overallScore = (0.4 * priceScore) + (0.3 * durationScore) + (0.3 * userRatingScore);	        

			// Round the overall score to 4 decimal places
			overallScore = Math.round(overallScore * 10000.0) / 10000.0;
			
			//System.out.println(flight.getFlightNumber() + " " + overallScore);
			scoreMap.put(flight, overallScore);
		}

		// Sort the flights by score in descending order
		List<Map.Entry<FlightInformation, Double>> sortedEntries = new ArrayList<>(scoreMap.entrySet());
		sortedEntries.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

		// Add the sorted flights to the result list
		for (Map.Entry<FlightInformation, Double> entry : sortedEntries) {
			// System.out.println(entry.getKey().getFlightNumber());
			result.add(entry.getKey());
		}

		return result;
	}

	public List<FlightInformation> aggregateBasedOnPriceDurationRatings(List<FlightInformation> flightList) {
		List<FlightInformation> result = new ArrayList<>();

		flightList.sort(Comparator.comparingDouble(f -> Double.parseDouble(((FlightInformation) f).getPrice()))
				.thenComparingDouble(f -> Double.parseDouble(((FlightInformation) f).getDuration()))
				.thenComparingDouble(f -> Double.parseDouble(((FlightInformation) f).getUserRatings())));

		result.addAll(flightList);

		return result;
	}

	public List<FlightInformation> sortBasedOnRatings(List<FlightInformation> flightList) {

		Comparator<FlightInformation> userRatingsComparator = (f1, f2) -> {
			// Convert the user ratings strings to doubles for sorting
			Double userRatings1 = Double.parseDouble(f1.getUserRatings());
			Double userRatings2 = Double.parseDouble(f2.getUserRatings());

			// Sort in descending order
			return userRatings2.compareTo(userRatings1);
		};
		return flightList.stream().sorted(userRatingsComparator).collect(Collectors.toList());
	}

}


/*
 * priceScore: This is calculated as the inverse of the price of the flight,
 * i.e. 1/price. The idea behind this calculation is that flights with lower
 * prices will get higher scores, as dividing by a lower price value will result
 * in a higher score value. 
 * 
 * durationScore: This is calculated as the inverse of
 * the duration of the flight, i.e. 1/duration. Here, the idea is that flights
 * with shorter durations will get higher scores, as dividing by a lower
 * duration value will result in a higher score value. 
 * 
 * userRatingScore: This is
 * simply the user rating score for the flight, which is assumed to be provided
 * as a numeric value. Once these score values are calculated for each flight,
 * the 
 * 
 * overall score for each flight is computed using the formula overallScore
 * = (0.5 * priceScore) + (0.3 * durationScore) + (0.2 * userRatingScore). This
 * formula takes into account the importance weights of each score component,
 * and generates an overall score that reflects the desired ranking criteria
 * (low price, short duration, and high user rating).
 * 
 * Finally, the flight list is sorted in descending order of overall score, so
 * that the flights with higher overall scores (i.e. better rankings) appear
 * first in the list.
 */