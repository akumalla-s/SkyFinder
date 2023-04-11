package neu.edu.skyfinder.controller.model;

public class UpdateUserModel {
	
	private String username;
	private String name;
	private String email;
	
	public UpdateUserModel() {
		// TODO Auto-generated constructor stub
	}

	public UpdateUserModel(String username, String name, String email) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
