package ca.mcgill.ecse321.gallerysystem.dto;

public class AdministratorDto {

	private String userRole = "admin";
	private String email;
	private String userName;
	private String password;
	
	public AdministratorDto() {
		
	}
	
	public AdministratorDto(String userName, String email, String password) {
		this.email = email;
		this.userName = userName;
		this.password = password;
	}

	public String getUserRole() {
		return this.userRole;
	}
	public String getUserName(){
		return this.userName;
	}
	public String getUserEmail() {
		return this.email;
	}
	public String getPassword() {
		return this.password;
	}
}
