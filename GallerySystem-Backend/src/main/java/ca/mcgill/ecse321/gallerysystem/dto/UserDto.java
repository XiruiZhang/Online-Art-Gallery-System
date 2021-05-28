package ca.mcgill.ecse321.gallerysystem.dto;

public class UserDto {
	
	private String userRole;
	private String email;
	private String userName;
	private String password;
	
	public UserDto() {
	
	}
	
	public UserDto(String userName, String email, String password, String userRole) {
		this.email  = email;
		this.password = password;
		this.userName = userName;
		this.userRole = userRole;
	}
	public String getEmail() {
		return this.email;
	}
	
	public String getUserRole() {
		return this.userRole;
	}
	public String getPassword() {
		return this.password;
	
	}
	public String getUserName() {
		return this.userName;
	}
	
}
