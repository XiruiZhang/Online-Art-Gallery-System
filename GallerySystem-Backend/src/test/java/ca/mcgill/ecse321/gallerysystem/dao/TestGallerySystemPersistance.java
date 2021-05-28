package ca.mcgill.ecse321.gallerysystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.gallerysystem.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest

public class TestGallerySystemPersistance {

	@Autowired
	private SelectedItemRepository selectedItemRepository;
	@Autowired
	private ArtPieceRepository artPieceRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdministratorRepository administratorRepository;
	

	@AfterEach
	public void clearDatabase() {
		shoppingCartRepository.deleteAll();
		userRepository.deleteAll();
		artistRepository.deleteAll();
		customerRepository.deleteAll();
		administratorRepository.deleteAll();
		artPieceRepository.deleteAll();
		orderRepository.deleteAll();
		selectedItemRepository.deleteAll();
		
	}
	
	@Test
	public void testPersistAndLoadCustomer() {
		
		Customer customer = new Customer();
	
		String customer_email = "customer_email";
		String customer_password = "customer_password";
		String customer_name = "customer_name";
		String address = "customer address";
		
		customer.setEmail(customer_email);
		customer.setPassword(customer_password);
		customer.setUserName(customer_name);
		customer.setAddress(address);

		customerRepository.save(customer);
		
		customer = null;
		
		customer = customerRepository.findCustomerByEmail(customer_email);
		assertNotNull(customer);
		assertEquals(customer_email, customer.getEmail());
		
	}
	
	@Test
	public void testPersistAndLoadArtist() {
		
		Artist artist = new Artist();
		
		String email = "artist_email";
		String password = "password";
		String name = "name";
		artist.setEmail(email);
		artist.setPassword(password);
		artist.setUserName(name);
		artistRepository.save(artist);
		
		artist = null;
		
		artist = artistRepository.findArtistByEmail(email);
		assertNotNull(artist);
		assertEquals(email, artist.getEmail());

	}
	
	@Test
	public void testPersistAndLoadArtPiece() {
//		
		ArtPiece art = new ArtPiece();
		Artist artist = new Artist();
		Set<ArtPiece> artPieces = new HashSet<ArtPiece>();
		
		String artist_email = "artist_email";
		String artist_password = "password";
		String artist_name = "name";
		
		Integer artID = 1;
		Integer discount = 70;
		float price = 1.1f;
		Integer quantity = 1;
		String artName = "artName";
		float commission = 20.0f;
		//String artistID = "artist1";
		//artist.setUserID(artistID);
		
		artist.setEmail(artist_email);
		artist.setPassword(artist_password);
		
		artist.setUserName(artist_name);
		artistRepository.save(artist);
		
		art.setDescription("");
		art.setDiscountPercentage(discount);
		art.setPrice(price);
		art.setQuantity(quantity);
		art.setArtID(artID);
		art.setArtName(artName);
		art.setArtist(artist);
		art.setCommissionPercentage(commission);
		artPieceRepository.save(art);
		
		artPieces.add(art);
		artist = null;
		art = null;
		artPieces = null;
		
		art = artPieceRepository.findArtPieceByArtID(artID);
		assertNotNull(art);
		assertEquals(art.getArtID(), artID);
		
		art = null;
		
		art = artPieceRepository.findArtPieceByArtistEmail(artist_email);
		assertNotNull(art);
		assertEquals(art.getArtID(), artID);
		
		art = null;
		
		artist = artistRepository.findArtistByEmail(artist_email);
		artPieces = artist.getArtPieces();
		art = artPieces.iterator().next();
		assertNotNull(artist.getArtPieces());
		assertEquals(art.getArtID(), artID);
		
	}
	
	@Test
	public void testPersistAndLoadShoppingCart() {

		Artist artist = new Artist();
		
		String artist_email = "artist_email";
		String artist_password = "password";
		String artist_name = "name";
		
		artist.setEmail(artist_email);
		artist.setPassword(artist_password);
		artist.setUserName(artist_name);
		artistRepository.save(artist);
	
		ArtPiece art = new ArtPiece();
		Integer artID = 1;
		Integer discount = 70;
		float price = 1.1f;
		Integer quantity = 1;
		String artName = "artName";
		art.setArtID(artID);
		art.setArtName(artName);
		art.setDescription("");
		art.setDiscountPercentage(discount);
		art.setPrice(price);
		art.setQuantity(quantity);
		art.setArtist(artist);
		artPieceRepository.save(art);
		
		Customer customer = new Customer();
		String customer_email = "customer_email";
		String customer_password = "password";
		String customer_name = "name";
		customer.setEmail(customer_email);
		customer.setPassword(customer_password);
		customer.setUserName(customer_name);
		customerRepository.save(customer);
		
		SelectedItem item = new SelectedItem();
		Integer itemID = 78;
		Integer itemQuantity = 20;
		item.setArtPiece(art);
		item.setItemID(itemID);
		item.setItemQuantity(itemQuantity);
		selectedItemRepository.save(item);
		Set<SelectedItem> items = new HashSet<SelectedItem>();
		items.add(item);
		
		ShoppingCart cart = new ShoppingCart();
		Integer cartID = 1;

		
		cart.setCartID(cartID);
		cart.setCustomer(customer);
		cart.setIsEmpty(true);
		cart.setSelectedItem(items);
		cart.setItemNumber(items.size());
		shoppingCartRepository.save(cart);
		
		customer = null;
		
		cart = shoppingCartRepository.findShoppingCartByCartID(cartID);
		customer = cart.getCustomer();
		items = cart.getSelectedItem();
		item = items.iterator().next();
		art = item.getArtPiece();
		assertNotNull(cart);
		assertNotNull(customer);
		assertNotNull(items);
		assertEquals(cart.getCartID(), cartID);
		assertEquals(customer.getEmail(), customer_email);
		assertEquals(item.getItemID(), itemID);
		assertEquals(art.getArtID(), artID);
		//assertEquals(cart, customer.getShoppingCart());
		//assertEquals(orders, customer.getOrders());

	}
	
	@Test
	public void testPersistAndLoadOrder() {

		Customer customer = new Customer();
		String customer_email = "customer_email";
		String customer_password = "password";
		String customer_name = "name";
		customer.setEmail(customer_email);
		customer.setPassword(customer_password);
		customer.setUserName(customer_name);
		customerRepository.save(customer);
		
		
		ShoppingCart cart = new ShoppingCart();
		Integer cartID = 1;
		cart.setCartID(cartID);
		cart.setCustomer(customer);
		cart.setIsEmpty(true);
		shoppingCartRepository.save(cart);
		
		Order order = new Order();
		Integer year = 2020;
		Integer month = 10;
		Integer day = 28;
		Date date = new Date(year, month, day);
		Integer orderNumber = 10;
		order.setCustomer(customer);
		order.setOrderDate(date);
		order.setOrderNumber(orderNumber);
		order.setShoppingCart(cart);
		orderRepository.save(order);
		
		order = null;
		customer = null;
		
		order = orderRepository.findCustomerByCustomerEmail(customer_email);
		customer = order.getCustomer();
		cart = order.getShoppingCart();
		assertNotNull(order);
		assertEquals(order.getOrderNumber(), orderNumber);
		assertNotNull(customer);
		assertEquals(customer.getEmail(), customer_email);
		assertNotNull(cart);
		assertEquals(cart.getCartID(), cartID);
		
	}
	
	@Test
	public void testPersistAndDeleteArtPiece() {
		
		ArtPiece art = new ArtPiece();
		String artist_email = "artist_email";
		art = artPieceRepository.findArtPieceByArtID(1);
		assertNotNull(art);
		assertEquals(art.getArtist().getEmail(), artist_email);
		//artPieceRepository.delete(art);
		//artPieceRepository.deleteById(1);
		art = artPieceRepository.deleteArtPieceByArtID(1);
		art = artPieceRepository.findArtPieceByArtID(1);
		assertNull(art);
		
	}
	
	
	
}


