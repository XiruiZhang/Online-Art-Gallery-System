package ca.mcgill.ecse321.gallerysystem.dto;

import javax.persistence.ManyToOne;

import ca.mcgill.ecse321.gallerysystem.model.ArtPiece;

public class SelectedItemDto {

	private Integer itemID;
	private Integer quantity;
	private ArtPiece artPiece;
	
	
	public SelectedItemDto(Integer itemID, Integer quantity, ArtPiece artPiece) {
		this.itemID = itemID;
		this.quantity = quantity;
		this.artPiece = artPiece;
	}
	
	public SelectedItemDto(ArtPiece artPiece) {
		this.artPiece = artPiece;
		this.quantity = 1;
		this.itemID = artPiece.getArtID();
	}
	
	public Integer getItemID() {
		return this.itemID;
	}
	
	public Integer getItemQuantity() {
		return this.quantity;
	}

	@ManyToOne(optional=false)
	public ArtPiece getArtPiece() {
		return this.artPiece;
	}
}
