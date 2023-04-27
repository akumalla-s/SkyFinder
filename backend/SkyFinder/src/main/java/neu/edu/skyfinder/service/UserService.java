package neu.edu.skyfinder.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import neu.edu.skyfinder.configuration.JwtTokenUtil;
import neu.edu.skyfinder.controller.model.CreateUserErrorResponse;
import neu.edu.skyfinder.controller.model.UpdateUserModel;
import neu.edu.skyfinder.controller.model.UpdateUserPasswordModel;
import neu.edu.skyfinder.controller.model.UpdateUserResponse;
import neu.edu.skyfinder.controller.model.UserModel;
import neu.edu.skyfinder.email.RegistrationEmailSenderService;
import neu.edu.skyfinder.entity.FlightBooking;
import neu.edu.skyfinder.entity.User;
import neu.edu.skyfinder.repository.FlightBookingRepository;
import neu.edu.skyfinder.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegistrationEmailSenderService emailSenderService;

	@Autowired
	private FlightBookingRepository bookingRepository;

	@Autowired
	private CreateUserErrorResponse errorResponse;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public User createUser(String name, String email, String username, String password) {
		User user = new User();

		user.setName(name);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setRole("USER");
		user = userRepository.saveAndFlush(user);
		try {
			emailSenderService.sendEmailDetails(email, username, name);
		} catch (Exception e) {
			System.out.println("Couldn't send registration email - User Service - createUser method");
			e.printStackTrace();
			return user;
		}
		return user;

	}

	public boolean checkUser(UserModel userModel) {
		Optional<User> user = userRepository.findById(userModel.getUsername());
		Optional<User> user2 = userRepository.findByEmail(userModel.getEmail());
		if (user.isPresent()) {
			String message = "User with username " + userModel.getUsername() + " already exists";
			errorResponse.setMessage(message);
			return true;
		} else if (user2.isPresent()) {
			String message = "User with email " + userModel.getEmail() + " already exists";
			errorResponse.setMessage(message);
			return true;
		}
		return false;
	}

	public boolean checkForUserUpdate(UpdateUserModel userModel, String oldusername) {
	    Optional<User> user = userRepository.findById(userModel.getUsername());
	    Optional<User> user2 = userRepository.findByEmail(userModel.getEmail());

	    if (user.isPresent() && !user.get().getUsername().equals(oldusername)) {
	        String message = "User with username " + userModel.getUsername() + " already exists";
	        errorResponse.setMessage(message);
	        return true;
	    } else if (user2.isPresent()) {
	    	Optional<User> userOld = userRepository.findById(oldusername);
	        if (!(user2.get().getEmail().equals(userOld.get().getEmail()))) {
	            String message = "User with email " + userModel.getEmail() + " already exists";
	            errorResponse.setMessage(message);
	            return true;
	        }
	    }

	    return false;
	}


	public boolean deleteUser(String username) {
		Optional<User> user = userRepository.findById(username);
		if (user.isPresent()) {
			userRepository.delete(user.get());
			return true;
		} else {
			return false;
		}
	}

	public User updateUserPassword(UpdateUserPasswordModel userModel, String username) {
		Optional<User> user = userRepository.findById(username);
		if (user.isPresent()) {
			User _user = user.get();
			_user.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
			_user = userRepository.save(_user);
			return _user;
		}
		System.out.println("User Service updatePassword Item is null");
		return null;
	}

	public UpdateUserResponse updateUser(UpdateUserModel userModel, String oldusername) {
		
		UpdateUserResponse response = new UpdateUserResponse();

		String old = oldusername;
		String newUsername = userModel.getUsername();
		Optional<User> user = userRepository.findById(oldusername);
		System.out.println(user);
		if (user.isPresent()) {
			User _user = user.get();
			System.out.println(_user);

			// Create a new user object with the updated properties
			User updatedUser = new User();
			updatedUser.setUsername(userModel.getUsername());
			updatedUser.setName(userModel.getName());
			updatedUser.setEmail(userModel.getEmail());
			updatedUser.setPassword(_user.getPassword());
			updatedUser.setRole("USER");

			// Save the updated user object
			updatedUser = userRepository.save(updatedUser);
			
			//Copy for response
			final String token = jwtTokenUtil.generateToken(updatedUser.getUsername());
			response.setUsername(updatedUser.getUsername());
			response.setEmail(updatedUser.getEmail());
			response.setName(updatedUser.getName());
			response.setRole(updatedUser.getRole());
			response.setToken(token);
			

			if (old.equals(newUsername)) {
				System.out.println("Not deleting username");
			} else {
				deleteUser(oldusername);
			}

			List<FlightBooking> flightBookings = bookingRepository.findByUsername(oldusername);
			for (int i = 0; i < flightBookings.size(); i++) {
				FlightBooking flightBooking = flightBookings.get(i);
				flightBooking.setUsername(newUsername);
				bookingRepository.save(flightBooking);
			}

			return response;
		}
		System.out.println("Item is null");
		return null;

	}

	public User getUserDetails(String username) {
		Optional<User> user = userRepository.findById(username);
		return user.get();
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();

		userRepository.findAll().forEach(users::add);
		return users;
	}

	public List<User> getAllUsersByRole(String role) {

		List<User> users = new ArrayList<>();
		if (role == null) {
			userRepository.findAll().forEach(users::add);

		} else {
			userRepository.findByRole(role).forEach(users::add);
		}
		return users;
	}

	public User register(String name, String email, String username, String password, String role) {
		User user = new User();
		user.setName(name);
		user.setUsername(username);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setEmail(email);
		user.setRole(role);
		user = userRepository.saveAndFlush(user);
		return user;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(username)
				.orElseThrow(() -> new RuntimeException("User not found: " + username));
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		System.out.println("In UserService Class " + user.getUsername());
		System.out.println("In UserService Class " + user.getRole());
		System.out.println("In UserService Class " + " ROle is being set");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Arrays.asList(authority));
	}

}
