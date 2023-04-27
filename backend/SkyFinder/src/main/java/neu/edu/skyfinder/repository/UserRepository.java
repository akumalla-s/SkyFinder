package neu.edu.skyfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import neu.edu.skyfinder.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	List<User> findByRole(String role);
	List<User> findByRoleIgnoreCase(String role);
	Optional<User> findByEmail(String email);
}
