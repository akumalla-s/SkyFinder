package neu.edu.skyfinder.controller.model;

public class UserModel {

	private String username;

	private String name;
	private String email;
	private String password;
	private String role;

	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public UserModel(String username, String name, String email, String password, String role) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
