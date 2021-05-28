package ca.mcgill.ecse321.gallerysystem.dto;


	//private Address address;


public class CustomerDto{

	private String address;

	private String email;
	private String userName;
	private String password;
	

	public CustomerDto(){	
	}
	
	public CustomerDto(String email) {
		this.email = email;
	}

	public CustomerDto(String userName, String address, String email,  String password) {
		this.address = address;
		this.email = email;
		this.userName = userName;
		this.password = password;
	
	}
	
	public String getAddress() {
		return this.address;
	}
	public String getEmail() {
		return this.email;
	}
	public String getUsername() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
}
