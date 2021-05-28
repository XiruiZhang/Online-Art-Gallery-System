package ca.mcgill.ecse321.gallerysystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.gallerysystem.model.Order;

@RepositoryRestResource(collectionResourceRel = "order_data", path = "order_data")

public interface OrderRepository extends CrudRepository<Order, Integer>{
	
	Order findOrderByOrderNumber(Integer orderNumber);
	
	Order findCustomerByCustomerEmail(String email);

	


}
