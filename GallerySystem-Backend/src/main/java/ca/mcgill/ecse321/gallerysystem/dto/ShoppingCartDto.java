package ca.mcgill.ecse321.gallerysystem.dto;

import java.util.Set;

import javax.persistence.OneToOne;

import ca.mcgill.ecse321.gallerysystem.model.Customer;
import ca.mcgill.ecse321.gallerysystem.model.SelectedItem;

public class ShoppingCartDto {
	
	private Integer itemNumber;
	private Integer cartID;
	private boolean isEmpty;
	private Customer customer;
	
	private Set<SelectedItem> selectedItem;
	
	
	public ShoppingCartDto(Integer itemNumber, Integer cartID, boolean isEmpty, Customer customer, Set<SelectedItem> selectedItem) {
		this.itemNumber = itemNumber;
		this.cartID = cartID;
		this.customer = customer;
		this.selectedItem = selectedItem;
		this.isEmpty = isEmpty;
		
		if(itemNumber == 0) {
			isEmpty = true;
		}
		
		else {
			isEmpty = false;
		}
	}
	
	
	public ShoppingCartDto(Customer customer) {
		this.customer = customer;
		this.isEmpty = true;
		this.cartID = customer.getShoppingCart().getCartID();
		this.itemNumber = 0;
	}
	
	public Boolean getIsEmpty() {
		return this.isEmpty;
	}
	
	
	public Integer getCartID() {
	
		return this.cartID;
	
	}
	
	public Integer getItemNumber() {
		
		return this.itemNumber;
		
	}
	
	@OneToOne(optional=false)
	public Customer getCustomer() {
		
	   return this.customer;
	   
	}
	

}
