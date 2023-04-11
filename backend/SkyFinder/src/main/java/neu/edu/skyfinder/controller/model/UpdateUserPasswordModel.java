package neu.edu.skyfinder.controller.model;

public class UpdateUserPasswordModel {

	private String password;

	public UpdateUserPasswordModel() {
		// TODO Auto-generated constructor stub
	}

	public UpdateUserPasswordModel(String password) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
