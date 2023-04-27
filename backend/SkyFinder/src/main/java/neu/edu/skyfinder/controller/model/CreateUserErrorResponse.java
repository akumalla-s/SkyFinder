package neu.edu.skyfinder.controller.model;

import org.springframework.stereotype.Service;

@Service
public class CreateUserErrorResponse {
	private String message;
	
	public CreateUserErrorResponse() {
		// TODO Auto-generated constructor stub
	}
	

	public CreateUserErrorResponse(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
