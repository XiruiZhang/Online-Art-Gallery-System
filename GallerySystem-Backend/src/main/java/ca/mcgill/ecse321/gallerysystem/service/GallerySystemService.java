package ca.mcgill.ecse321.gallerysystem.service;

import java.sql.Date;

// Implementing use cases


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ca.mcgill.ecse321.gallerysystem.dao.*;
import ca.mcgill.ecse321.gallerysystem.model.*;

@Service
public class GallerySystemService {

	@Autowired
	SelectedItemRepository selectedItemRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdministratorRepository administratorRepository;
	@Autowired 
	ArtPieceRepository artPieceRepository;
	
	
	
	@Transactional 
	public User getUser(String email) {
		User user = userRepository.findUserByEmail(email);
		return user;
	}
	
	
	@Transactional 
	public ArtPiece createArtPiece(String artName, Integer quantity, float price, Integer discountPercentage, Float commissionPercentage, String description, Integer artID, String artistEmail) {
		 if(artName == null || quantity == null|| quantity <= 0 || price < 0|| discountPercentage == null || discountPercentage < 0 || description == null|| artID == null|| artistEmail == null || commissionPercentage == null || commissionPercentage < 0 ) {
			 throw new IllegalArgumentException("Invalid Input!");
		 }
		 
		
		 ArtPiece artpiece = new ArtPiece();
		 artpiece.setQuantity(quantity);
		 artpiece.setPrice(price);
		 artpiece.setDiscountPercentage(discountPercentage);
		 artpiece.setCommissionPercentage(commissionPercentage);
		 artpiece.setArtName(artName);
		 artpiece.setDescription(description);
		 artpiece.setArtID(artID);
		 artpiece.setArtist(artistRepository.findArtistByEmail(artistEmail));
		 
		 
		
		artPieceRepository.save(artpiece);
		//artistRepository.findArtistByEmail(artistEmail).getArtPieces().add(artpiece);
		return artpiece;
	}
	
	
	@Transactional
	public ArtPiece getArtpiece(Integer artID) {
		ArtPiece artpiece = artPieceRepository.findArtPieceByArtID(artID);
		return artpiece;
	}
	
	@Transactional 
	public List<ArtPiece> getAllArtPieces() {
		return toList(artPieceRepository.findAll());
	}

	
	@Transactional
	public Customer createCustomer(String userName,  String email, String address, String password) {
		if(userName == null || email == null|| address == null || password == null) {
			throw new IllegalArgumentException("Invalid Input!");
		}
		Customer customer  = new Customer();
		customer.setAddress(address);
		customer.setEmail(email);
		customer.setUserName(userName);
		customer.setPassword(password);
		
		
		customerRepository.save(customer);
		return customer;	
	}
	
	@Transactional 
	public Customer createCustomer(Customer customer) {
		Customer updatedCustomer  = customer;

		customerRepository.save(updatedCustomer);
		return updatedCustomer;	
		
	}
	

	@Transactional
	public Customer getCustomer(String email) {
		Customer customer = customerRepository.findCustomerByEmail(email);
		return customer;	
	}
	
	
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(customerRepository.findAll());
	}
	
	@Transactional 
	public void deleteCustomer(String email) {
		customerRepository.deleteById(email);	
	}
	
	@Transactional
	public void deleteAllCustomers() {
		customerRepository.deleteAll();
	}
	
	@Transactional
	public Artist createArtist(String userName,  String email, String password) {
		if(userName == null || email == null || password == null) {
			 throw new IllegalArgumentException("Invalid Input!");
		}
		Artist artist  = new Artist();
		artist.setEmail(email);
		artist.setUserName(userName);
		artist.setPassword(password);
		
		
		artistRepository.save(artist);
		return artist;	
	}
	
	@Transactional
	public Artist getArtist(String email) {
		Artist artist = artistRepository.findArtistByEmail(email);
		return artist;	
	}
	  
	@Transactional
	public List<Artist> getAllArtists() {
		return toList(artistRepository.findAll());
	}

	
	@Transactional
	public void deleteArtist(String email) {
		artistRepository.deleteById(email);
		
	}
	@Transactional
	public void deleteAllArtists() {
		artistRepository.deleteAll();
	}

	
	@Transactional
	public Administrator createAdministrator(String userName, String email, String password) {
		if(userName == null || email == null || password == null) {
			 throw new IllegalArgumentException("Invalid Input!");
		}
		Administrator administrator  = new Administrator();
		administrator.setEmail(email);
		administrator.setUserName(userName);
		administrator.setPassword(password);
		
		
		administratorRepository.save(administrator);
		return administrator;	
	}
	
	@Transactional
	public List<Administrator> getAllAdministrators() {
		return toList(administratorRepository.findAll());
	}
	
	@Transactional
	public Administrator getAdministrator(String adminEmail) {
		Administrator admin = administratorRepository.findArtistByEmail(adminEmail);
		return admin;
	}
	@Transactional
	public void deleteAdministrator(String email) {
		administratorRepository.deleteById(email);
		
	}
	@Transactional
	public void deleteAllAdministrators() {
		administratorRepository.deleteAll();
	}
	
	@Transactional
	public List<User> getAllUsers(){
		return toList(userRepository.findAll());
	}
	
	@Transactional
	public ShoppingCart createShoppingCart(Integer itemNumber, Integer cartID, boolean isEmpty, Customer customer) {
		if(itemNumber == null || cartID == null || customer == null) {
			 throw new IllegalArgumentException("Invalid Input!");
		}
		ShoppingCart sc = new ShoppingCart();
		sc.setCartID(cartID);
		sc.setCustomer(customer);
		sc.setItemNumber(itemNumber);
		sc.setIsEmpty(isEmpty);
		return sc;
	}
	
	@Transactional
	public ShoppingCart getShoppingCart(String email) {
		ShoppingCart sc = shoppingCartRepository.findShoppingCartByCustomerEmail(email);
		return sc;
	}
	
	@Transactional
	public List<ShoppingCart> getAllShoppingCart(){
		return toList(shoppingCartRepository.findAll());
	}
	
	@Transactional
	public void deleteShoppingCart(Integer cartID) {
		shoppingCartRepository.deleteById(cartID);
		
	}
	@Transactional
	public void deleteAllShoppingCarts() {
		artistRepository.deleteAll();
	}
	
	@Transactional
	public SelectedItem getSelectedItem(Integer itemID) {
		SelectedItem si = selectedItemRepository.findSelectedItemByItemID(itemID);
		return si;
	}
	
	@Transactional
	public SelectedItem createSelectedItem(Integer itemID, Integer quantity, ArtPiece artPiece) {
		if(itemID == null || quantity == null || quantity <=0 || artPiece == null) {
			 throw new IllegalArgumentException("Invalid Input!");
		}
		SelectedItem si = new SelectedItem();
		si.setArtPiece(artPiece);
		si.setItemID(itemID);
		si.setItemQuantity(quantity);
		return si;
	}
	
	@Transactional
	public List<SelectedItem> getAllSelectedItem(){
		return toList(selectedItemRepository.findAll());
	}
	
	@Transactional
	public void deleteSelectedItem(Integer itemID) {
		selectedItemRepository.deleteById(itemID);
		
	}
	@Transactional
	public void deleteAllSelectedItems() {
		selectedItemRepository.deleteAll();
	}
	
	@Transactional
	public Order createOrder(Integer ordernumber, Date orderDate, ShoppingCart shoppingCart, Customer customer) {
		if(ordernumber == null || orderDate == null || shoppingCart == null || customer == null) {
			 throw new IllegalArgumentException("Invalid Input!");
		}
		Order o = new Order();
		o.setOrderNumber(ordernumber);
		o.setOrderDate(orderDate);
		o.setShoppingCart(shoppingCart);
		o.setCustomer(customer);
		return o;
	}
	
	
	
	@Transactional
	public Order getOrder(Integer ordernumber) {
		Order o = orderRepository.findOrderByOrderNumber(ordernumber);
		return o;
	}
	
	@Transactional
	public List<Order> getAllOrder(){
		return toList(orderRepository.findAll());
	}
	
	@Transactional
	public void deleteOrder(Integer orderNumber) {
		orderRepository.deleteById(orderNumber);
		
	}
	
	@Transactional
	public void deleteAllOrders() {
		orderRepository.deleteAll();
	}
	
	
	@Transactional 
	public void deleteArtpiece(Integer artID) {
		artPieceRepository.deleteById(artID);
		
	}
	@Transactional
	public void deleteAllArtPieces() {
		artPieceRepository.deleteAll();
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	
	
	

}
