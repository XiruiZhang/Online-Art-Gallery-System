package ca.mcgill.ecse321.gallerysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.gallerysystem.dao.AdministratorRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ArtistRepository;
import ca.mcgill.ecse321.gallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.gallerysystem.dao.OrderRepository;
import ca.mcgill.ecse321.gallerysystem.dao.SelectedItemRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ShoppingCartRepository;
import ca.mcgill.ecse321.gallerysystem.dao.UserRepository;
import ca.mcgill.ecse321.gallerysystem.model.Administrator;
import ca.mcgill.ecse321.gallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.gallerysystem.model.Artist;
import ca.mcgill.ecse321.gallerysystem.model.Customer;
import ca.mcgill.ecse321.gallerysystem.model.Order;
import ca.mcgill.ecse321.gallerysystem.model.SelectedItem;
import ca.mcgill.ecse321.gallerysystem.model.ShoppingCart;
import ca.mcgill.ecse321.gallerysystem.model.User;

@ExtendWith(MockitoExtension.class)
public class TestGallerySystemService {
	
	@Mock
	private ArtistRepository artistDao;
	@Mock
	private AdministratorRepository administratorDao;
	@Mock
	private ArtPieceRepository artPieceDao;
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private OrderRepository orderDao;
	@Mock
	private SelectedItemRepository selectedItemDao;
	@Mock
	private ShoppingCartRepository shoppingCartDao;
	@Mock
	private UserRepository userDao;
	
	@InjectMocks
	private GallerySystemService service;
	
	private static final String ARTIST_KEY = "TestArtist";
	private static final String NONEXISTING_KEY = "NotAnArtist";
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(artistDao.findArtistByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTIST_KEY)) {
				Artist artist = new Artist();
				artist.setEmail(ARTIST_KEY);
				return artist;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(artistDao.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(administratorDao.save(any(Administrator.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(artPieceDao.save(any(ArtPiece.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(orderDao.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(selectedItemDao.save(any(SelectedItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(shoppingCartDao.save(any(ShoppingCart.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(userDao.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateArtist() {
		assertEquals(0, service.getAllArtists().size());
		
		String userName = "John";
		String email = "john@mail.com";
		String password = "pass";
		Artist artist = null;
		try {
			artist = service.createArtist(userName, email, password);
		}catch (IllegalArgumentException e) {
			fail();
		}
		checkResultCreateArtist(artist, userName, email, password);
	}
	
	private void checkResultCreateArtist(Artist artist, String userName, String email, String password) {
		assertNotNull(artist);
		assertEquals(userName, artist.getUserName());
		assertEquals(email, artist.getEmail());
		assertEquals(password, artist.getPassword());
	}
	
	@Test
	public void testCreateArtistNull() {
		assertEquals(0, service.getAllArtists().size());
		String error = null;
		String userName = null;
		String email = null;
		String password = null;
		Artist artist = null;
		try {
			artist = service.createArtist(userName, email, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(artist);
		assertEquals("Invalid Input!", error);
	}
	
	@Test
	public void testCreate2ArtistSameEmail() {
		assertEquals(0, service.getAllArtists().size());
		
		String userName = "John";
		String userName2 = "Adam";
		String email = "john@mail.com";
		String password = "pass";
		Artist artist = null;
		Artist artist2 = null;
		artist = service.createArtist(userName, email, password);
		artist2 = service.createArtist(userName2, email, password);
		checkResultCreateArtist(artist, userName, email, password);
		checkResultCreateArtist(artist2, userName2, email, password);
	}
	
	@Test
	public void testCreateArtPiece() {
		assertEquals(0, service.getAllArtPieces().size());
		
		String artName = "Riverside Tree";
		Integer quantity = 3;
		float price = 800;
		Integer discountPercentage = 10;
		Float commission = (float) 23.0;
		String description = "tree on a riverside";
		Integer artID = 84356;
		
		
		String userName = "John";
		String email = "john@mail.com";
		String password = "pass";
		Artist artist = service.createArtist(userName, email, password);
		lenient().when(artistDao.existsById(anyString())).thenReturn(true);
		ArtPiece artPiece = null;
		
		try {
			artPiece = service.createArtPiece(artName, quantity, price, discountPercentage, commission, description, artID, artist);
		}catch (IllegalArgumentException e) {
			fail();
		}
		checkResultCreateArtPiece(artPiece, artName, quantity, price, discountPercentage, commission, description, artID, userName, email, password);
	}
	
	private void checkResultCreateArtPiece(ArtPiece artPiece, String artName, Integer quantity, float price, Integer discountPercentage, Float commissionPercentage, String description, Integer artID,
			String userName, String email, String password) {
		assertNotNull(artPiece);
		assertEquals(artName, artPiece.getArtName());
		assertEquals(quantity, artPiece.getQuantity());
		assertEquals(discountPercentage, artPiece.getDiscountPercentage());
		assertEquals(description, artPiece.getDescription());
		assertEquals(artID, artPiece.getArtID());
		assertEquals(userName, artPiece.getArtist().getUserName());
		assertEquals(email, artPiece.getArtist().getEmail());
		assertEquals(password, artPiece.getArtist().getPassword());
		assertEquals((float) commissionPercentage, artPiece.getCommissionPercentage());
	}
	
	@Test
	public void testInvalidCreateArtPiece() {
		assertEquals(0, service.getAllArtPieces().size());
		
		String error = null;
		String artName = "Riverside Tree";
		Integer quantity = -7;
		float price = 800;
		Integer discountPercentage = 10;
		Float commissionPercentage = (float) 24; //To be removed as it is never used.
		String description = "tree on a riverside";
		Integer artID = 84356;
		Float commission = (float) 5.0;
		
		String userName = "John";
		String email = "john@mail.com";
		String password = "pass";
		Artist artist = service.createArtist(userName, email, password);
		lenient().when(artistDao.existsById(anyString())).thenReturn(true);
		ArtPiece artPiece = null;
		
		try {

			artPiece = service.createArtPiece(artName, quantity, price, discountPercentage, commission, description, artID, artist);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(artPiece);
		assertEquals("Invalid Input!", error);
	}
	
	@Test
	public void testCreateCustomer() {
		assertEquals(0, service.getAllCustomers().size());
		
		String userName = "David";
		String email = "david@mail.com";
		String password = "pass";
		String address = "North Carol Drive #86";
		Customer customer = null;
		try {
			customer = service.createCustomer(userName, email, address, password);
		}catch (IllegalArgumentException e) {
			fail();
		}
		checkResultCreateCustomer(customer, userName, email, password, address);
	}
	
	private void checkResultCreateCustomer(Customer customer, String userName, String email, String password, String address) {
		assertNotNull(customer);
		assertEquals(userName, customer.getUserName());
		assertEquals(email, customer.getEmail());
		assertEquals(password, customer.getPassword());
		assertEquals(address, customer.getAddress());


	}
	
	@Test
	public void testInvalidCreateCustomer() {
		assertEquals(0, service.getAllCustomers().size());
		
		String error = null;
		String userName = null;
		String email = "david@mail.com";
		String password = "pass";
		String address = "North Carol Drive #86";
		Customer customer = null;
		try {
			customer = service.createCustomer(userName, email, address, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(customer);
		assertEquals("Invalid Input!", error);	
	}
	

	
	@Test
	public void testCreateOrder() {
		assertEquals(0, service.getAllOrder().size());
		
		Integer ordernumber = 21;
		Date orderDate = new Date(2020, 8, 14);
		Integer itemNumber = 72;
		Integer cartID = 2;
		String userName = "David";
		String email = "david@mail.com";
		String password = "pass";
		String address = "North Carol Drive #86";
		Customer customer = service.createCustomer(userName, email, address, password);
		Boolean isEmpty = false;
		ShoppingCart shoppingCart = service.createShoppingCart(itemNumber, cartID, isEmpty, customer);
		Order order = null;
		try {
			order = service.createOrder(ordernumber, orderDate, shoppingCart, customer);

		}catch (IllegalArgumentException e) {
			fail();
		}
		checkResultCreateOrder(order, ordernumber, orderDate, userName, email, password, address, itemNumber, cartID);
	}
	
	private void checkResultCreateOrder(Order order, Integer ordernumber, Date orderDate, String userName, String email, String password, String address, Integer itemNumber,
			Integer cartID) {
		assertNotNull(order);
		assertEquals(userName, order.getCustomer().getUserName());
		assertEquals(email, order.getCustomer().getEmail());
		assertEquals(password, order.getCustomer().getPassword());
		assertEquals(address, order.getCustomer().getAddress());
		assertEquals(itemNumber, order.getShoppingCart().getItemNumber());
		assertEquals(cartID, order.getShoppingCart().getCartID());
		assertEquals(ordernumber, order.getOrderNumber());
		assertEquals(orderDate, order.getOrderDate());


	}
	
	@Test
	public void testInvalidCreateOrder() {
		assertEquals(0, service.getAllOrder().size());
		
		String error = null;
		Integer ordernumber = 21;
		Date orderDate = null;
		Integer itemNumber = 72;
		Integer cartID = 2;
		String userName = "David";
		String email = "david@mail.com";
		String password = "pass";
		String address = "North Carol Drive #86";
		Customer customer = service.createCustomer(userName, email, address, password);
		Boolean isEmpty = false;
		ShoppingCart shoppingCart = service.createShoppingCart(itemNumber, cartID, isEmpty, customer);
		Order order = null;
		try {
			order = service.createOrder(ordernumber, orderDate, shoppingCart, customer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(order);
		assertEquals("Invalid Input!", error);
	}
	
	@Test
	public void testCreateSelectedItem() {
		assertEquals(0, service.getAllSelectedItem().size());
		
		Integer itemID = 1;
		Integer quantity = 1;
		ArtPiece artPiece = new ArtPiece();
		SelectedItem si = null;
		
		try {
			si = service.createSelectedItem(itemID, quantity, artPiece);
			
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(si);
		assertEquals(itemID, si.getItemID());
		assertEquals(quantity, si.getItemQuantity());
		assertEquals(artPiece, si.getArtPiece());
	}
	
	@Test
	public void testInvalidCreateSelectedItem() {
		assertEquals(0, service.getAllSelectedItem().size());
		
		String error = null;
		Integer itemID = 14;
		Integer quantity = 0;
		ArtPiece artPiece = new ArtPiece();
		SelectedItem selectedItem = null;
		try {
			selectedItem = service.createSelectedItem(itemID, quantity, artPiece);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(selectedItem);
		assertEquals("Invalid Input!", error);
	}

		
	
	@Test
	public void testCreateShoppingCart() {
		assertEquals(0, service.getAllShoppingCart().size());
		
		Integer itemNumber = 1;
		Integer cartID = 1234;
		boolean isEmpty = false;
		Customer customer = new Customer();
		ShoppingCart sc = null;
		
		try {
			sc = service.createShoppingCart(itemNumber, cartID, isEmpty, customer);	
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(sc);
		assertEquals(itemNumber, sc.getItemNumber());
		assertEquals(cartID, sc.getCartID());
		assertEquals(isEmpty, sc.isIsEmpty());
		assertEquals(customer, sc.getCustomer());
	}
	
	@Test
	public void testInvalidCreateShoppingCart() {
		assertEquals(0, service.getAllShoppingCart().size());
		
		String error = null;
		Integer itemNumber = null;
		Integer cartID = 2;
		Customer customer = new Customer();
		Boolean isEmpty = false;
		ShoppingCart shoppingCart = null;
		try {
			shoppingCart = service.createShoppingCart(itemNumber, cartID, isEmpty, customer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(shoppingCart);
		assertEquals("Invalid Input!", error);
	}

	
	@Test
	public void testGetExistingArtist() {
		assertEquals(ARTIST_KEY, service.getArtist(ARTIST_KEY).getEmail());
	}

	@Test
	public void testGetNonExistingArtist() {
		assertNull(service.getArtist(NONEXISTING_KEY));
	}

		
	
}
