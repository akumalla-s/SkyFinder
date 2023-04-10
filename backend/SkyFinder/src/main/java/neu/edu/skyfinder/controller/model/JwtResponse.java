package neu.edu.skyfinder.controller.model;

public class JwtResponse {
	private final String token;
	private final String username;
	private final String role;
	
	

	public JwtResponse(String token, String username, String role) {
		this.token = token;
		this.username = username;
		this.role = role;
	}

	public String getToken() {
		return this.token;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getRole() {
		return this.role;
	}

}
