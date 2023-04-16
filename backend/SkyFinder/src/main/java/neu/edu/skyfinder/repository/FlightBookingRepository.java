package neu.edu.skyfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import neu.edu.skyfinder.entity.FlightBooking;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Integer> {

	List<FlightBooking> findByUsername(String username);

}
