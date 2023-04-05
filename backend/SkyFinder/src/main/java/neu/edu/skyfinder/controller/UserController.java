package neu.edu.skyfinder.controller;

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


import neu.edu.skyfinder.entity.User;
import neu.edu.skyfinder.service.UserService;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String testApp() {
		return "Hello from SkyFinder!!";
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@RequestBody UserModel userModel) {
		User users = userService.createUser(userModel.getName(),userModel.getEmail(),userModel.getUsername(),userModel.getPassword());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@DeleteMapping("{username}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable String username){
		boolean isDeleted = userService.deleteUser(username);
		return new ResponseEntity<>(isDeleted, HttpStatus.OK);
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<User> updateUser(@RequestBody UserModel userModel){
		User user = userService.updateUser(userModel);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("{username}")
	public ResponseEntity<User> getUserDetails(@PathVariable String username){
		User user = userService.getUserDetails(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}