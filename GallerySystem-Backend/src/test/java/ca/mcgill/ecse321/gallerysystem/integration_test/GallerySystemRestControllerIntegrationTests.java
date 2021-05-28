package ca.mcgill.ecse321.gallerysystem.integration_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

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
import ca.mcgill.ecse321.gallerysystem.service.GallerySystemService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SpringBootApplication.class, 
        webEnvironment = WebEnvironment.RANDOM_PORT)
public class GallerySystemRestControllerIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
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
    public void testAddArtist() {
        String userName = "John";
		String email = "john@mail.com";
		String password = "pass";
		Artist artist = null;
		try {
			artist = service.createArtist(userName, email, password);
		}catch (IllegalArgumentException e) {
			fail();
        }
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/artist", artist, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }
    
    @Test
    public void testAddArtPiece() {
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
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/artpiece", artPiece, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test 
    public void testAddCustomer() {
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
        
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/customer", customer, String.class);
            assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testAddOrder() {
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
        
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/order", order, String.class);
            assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testAddShoppingCart() {
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
        
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity("http://localhost:" + port + "/shoppingCart", sc, String.class);
            assertEquals(201, responseEntity.getStatusCodeValue());
    }
}
