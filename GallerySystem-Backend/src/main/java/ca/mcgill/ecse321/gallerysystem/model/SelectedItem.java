package ca.mcgill.ecse321.gallerysystem.model;

import javax.persistence.Entity;

import javax.persistence.Id;


import javax.persistence.ManyToOne;

@Entity
public class SelectedItem {
	private Integer itemID;
	private Integer quantity;
	private ArtPiece artPiece;

	public void setItemID(Integer value) {
		this.itemID = value;
	}

	@Id
	public Integer getItemID() {
		return this.itemID;
	}

	

	public void setItemQuantity(Integer value) {
		this.quantity = value;
	}

	public Integer getItemQuantity() {
		return this.quantity;
	}

	

	@ManyToOne(optional=false)
	public ArtPiece getArtPiece() {
		return this.artPiece;
	}

	public void setArtPiece(ArtPiece artPiece) {
		this.artPiece = artPiece;
	}

}
