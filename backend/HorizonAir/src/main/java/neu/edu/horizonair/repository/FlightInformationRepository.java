package neu.edu.horizonair.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import neu.edu.horizonair.entity.FlightInformation;


@Repository
public interface FlightInformationRepository extends JpaRepository<FlightInformation, String> {
	
	List<FlightInformation> findByOriginAndDestinationAndStartDate(String origin, String destination, Date startDate);
    
}
