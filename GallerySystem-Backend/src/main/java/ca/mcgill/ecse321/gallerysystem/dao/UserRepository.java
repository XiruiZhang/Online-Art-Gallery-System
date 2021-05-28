package ca.mcgill.ecse321.gallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.gallerysystem.model.*;




@RepositoryRestResource(collectionResourceRel = "user_data", path = "user_data")
public interface UserRepository extends CrudRepository<User, String>{
	
	User findUserByEmail(String email);

}
