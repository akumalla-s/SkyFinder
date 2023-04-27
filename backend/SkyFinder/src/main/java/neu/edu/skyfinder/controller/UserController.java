package neu.edu.skyfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import neu.edu.skyfinder.controller.model.CreateUserErrorResponse;
import neu.edu.skyfinder.controller.model.UpdateUserModel;
import neu.edu.skyfinder.controller.model.UpdateUserPasswordModel;
import neu.edu.skyfinder.controller.model.UserModel;
import neu.edu.skyfinder.entity.User;
import neu.edu.skyfinder.service.UserService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CreateUserErrorResponse errorResponse;

	@GetMapping("/testApp")
	public String testApp() {
		return "Hello from SkyFinder!!";
	}

	@GetMapping("/getAllusers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsersByRole("USER");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody UserModel userModel) {
		User user = null;
		boolean isUserExists = userService.checkUser(userModel);

		if (isUserExists) {
			String message = errorResponse.getMessage();
			return ResponseEntity.status(HttpStatus.OK).body(new CreateUserErrorResponse(message));

		} else {
			user = userService.createUser(userModel.getName(), userModel.getEmail(), userModel.getUsername(),
					userModel.getPassword());
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

	}

	@DeleteMapping("{username}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable String username) {
		boolean isDeleted = userService.deleteUser(username);
		return new ResponseEntity<>(isDeleted, HttpStatus.OK);
	}

	@PutMapping("/updateUser/{oldusername}")
	public ResponseEntity<?> updateUser(@RequestBody UpdateUserModel userModel, @PathVariable String oldusername) {
		boolean isUserExists = false;
		User user = null;
		if(userModel.getUsername().equals(oldusername)) {
			isUserExists = false;
		}else {
			isUserExists = userService.checkForUserUpdate(userModel, oldusername); 
		}		

		if (isUserExists) {
			String message = errorResponse.getMessage();
			return ResponseEntity.status(HttpStatus.OK).body(new CreateUserErrorResponse(message));

		} else {
			user = userService.updateUser(userModel, oldusername);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

	}

	@PutMapping("/updateUserPassword/{username}")
	public ResponseEntity<User> updateUserPassword(@RequestBody UpdateUserPasswordModel userModel,
			@PathVariable String username) {
		User user = userService.updateUserPassword(userModel, username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("{username}")
	public ResponseEntity<User> getUserDetails(@PathVariable String username) {
		System.out.println("********* Username ********** " + username);
		User user = userService.getUserDetails(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
