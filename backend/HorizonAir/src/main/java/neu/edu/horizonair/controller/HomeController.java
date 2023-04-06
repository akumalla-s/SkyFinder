package neu.edu.horizonair.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String TestApp() {
		return "Hello from Horizon Air";
	}
}
