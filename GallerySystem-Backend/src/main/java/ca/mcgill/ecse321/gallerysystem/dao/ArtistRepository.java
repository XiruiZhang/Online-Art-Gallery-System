package ca.mcgill.ecse321.gallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.gallerysystem.model.*;

@RepositoryRestResource(collectionResourceRel = "artist_data", path = "artist_data")
public interface ArtistRepository extends CrudRepository<Artist, String>{
	
	//Artist findArtistByUserID(String artistID);
	
	Artist findArtistByEmail(String email);

}
