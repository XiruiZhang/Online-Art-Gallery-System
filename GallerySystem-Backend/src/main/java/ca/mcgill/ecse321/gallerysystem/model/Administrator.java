package ca.mcgill.ecse321.gallerysystem.model;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {
	
	private String userRole = "admin";

	
	public void setUserRole(String value) {
		this.userRole = value;
	}

	public String getUserRole() {
		return this.userRole;
	}


}
