package ca.mcgill.ecse321.gallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.gallerysystem.model.*;

@RepositoryRestResource(collectionResourceRel = "selectedItem_data", path = "selectedItem_data")
public interface SelectedItemRepository extends CrudRepository<SelectedItem, Integer>{
	
	SelectedItem findSelectedItemByItemID(Integer itemID);

}
