package neu.edu.skyfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import neu.edu.skyfinder.service.UserService;

@SpringBootApplication
@EnableWebMvc
public class SkyFinderApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SkyFinderApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		userService.register("Admin", "admin@gmail.com", "admin", "password", "ADMIN");
		userService.register("Ashwin","ashwin@a.com","username","password", "USER");
		userService.register("Test","test@gmail.com","test","password", "USER");
		
	}

}
