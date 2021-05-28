package ca.mcgill.ecse321.gallerysystem.dto;

import java.util.Collections;
import java.util.List;




import ca.mcgill.ecse321.gallerysystem.model.ArtPiece;



public class ArtistDto{

	private List<ArtPiece> artPieces;
	private String email;
	private String userName;
	private String password;
	

	public ArtistDto(){	
	}
	
	@SuppressWarnings("unchecked")
	public ArtistDto(String name) {
		this(name, Collections.EMPTY_LIST);
	}
	public ArtistDto(String name, List<ArtPiece> arrayList) {
		this.userName = name;
		this.artPieces = arrayList;
	}
	
	public ArtistDto(String userName, String email,  String password) {
		this.email = email;
		this.userName = userName;
		this.password = password;
	
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
	
	public List<ArtPiece> getArtPieces() {
		return this.artPieces;
	}

	public void setArtPieces(List<ArtPiece> artPieces) {
		this.artPieces = artPieces;
	}
	
}
