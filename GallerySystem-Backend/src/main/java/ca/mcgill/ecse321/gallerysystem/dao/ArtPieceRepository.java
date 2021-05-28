package ca.mcgill.ecse321.gallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.gallerysystem.model.ArtPiece;

@RepositoryRestResource(collectionResourceRel = "artpiece_data", path = "artpiece_data")
public interface ArtPieceRepository extends CrudRepository<ArtPiece, Integer>{
	
	ArtPiece findArtPieceByArtID(Integer artID);
	
	ArtPiece findArtPieceByArtistEmail(String email);
	
	ArtPiece deleteArtPieceByArtID(Integer artID);
	
}
