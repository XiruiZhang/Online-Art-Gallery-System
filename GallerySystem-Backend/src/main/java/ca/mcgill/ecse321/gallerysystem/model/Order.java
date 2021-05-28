package ca.mcgill.ecse321.gallerysystem.model;

import javax.persistence.Entity;

import javax.persistence.Id;
import java.sql.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "orders")
public class Order {

	private Integer orderNumber;
	private Date orderDate; 
	private ShoppingCart shoppingCart;
	private Customer customer;
	
	public void setOrderNumber(Integer value) {
		this.orderNumber = value;
	}

	@Id
	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	

	public void setOrderDate(Date value) {
		this.orderDate = value;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	

	@ManyToOne(optional = false)
	public ShoppingCart getShoppingCart() {
		return this.shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
