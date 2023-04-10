package neu.edu.skyfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import neu.edu.skyfinder.service.UserService;

@SpringBootApplication
public class SkyFinderApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SkyFinderApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		userService.register("Ashwin","ashwin@a.com","username","password");
		userService.register("Srinivasa Reddy","srini@gmail.com","srinredd","password");
		userService.register("Test","test@gmail.com","test","password");
		
	}

}
