package neu.edu.skyfinder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.skyfinder.controller.UserModel;
import neu.edu.skyfinder.entity.User;
import neu.edu.skyfinder.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(String name, String email, String username, String password) {
		User user = new User();

		user.setName(name);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole("USER");
		user = userRepository.saveAndFlush(user);
		return user;

	}
	
	public boolean deleteUser(String username) {
		Optional<User> user = userRepository.findById(username);
		if(user.isPresent()) {
			userRepository.delete(user.get());
			return true;
		}else {
			return false;
		}
	}
	
	public User updateUser(UserModel userModel) {
		Optional<User> user = userRepository.findById(userModel.getUsername());
		System.out.println(user);
		if(user.isPresent()) {
			User _user = user.get();
			System.out.println(_user);
			
			_user.setName(userModel.getName());
			_user.setEmail(userModel.getEmail());
			_user.setPassword(userModel.getPassword());
			_user.setRole("USER");
			
			_user = userRepository.save(_user);
			
			return _user;
		}
		System.out.println("Item is null");
		return null;
		
	}

	public User getUserDetails(String username) {
		Optional<User> user = userRepository.findById(username);
		return user.get();
	}
	
	

}
