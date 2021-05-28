package ca.mcgill.ecse321.gallerysystem.model;

import javax.persistence.Entity;

import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

	private String email;
	private String userName;
	private String password;
	private String userRole;
	
	public void setEmail(String value) {
		this.email = value;
	}
	
	@Id
	public String getEmail() {
		return this.email;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public void setUserRole(String value) {
		this.userRole = value;
	}
	public String getUserRole() {
		return this.userRole;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}
	
}
