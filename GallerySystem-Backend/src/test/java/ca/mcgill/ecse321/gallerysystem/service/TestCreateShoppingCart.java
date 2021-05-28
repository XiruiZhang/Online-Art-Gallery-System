package ca.mcgill.ecse321.gallerysystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.gallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ShoppingCartRepository;
import ca.mcgill.ecse321.gallerysystem.dao.UserRepository;
import ca.mcgill.ecse321.gallerysystem.model.Customer;
import ca.mcgill.ecse321.gallerysystem.model.ShoppingCart;
import ca.mcgill.ecse321.gallerysystem.model.User;

@ExtendWith(MockitoExtension.class)
public class TestCreateShoppingCart {
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private ShoppingCartRepository shoppingCartDao;
	@Mock
	private UserRepository userDao;
	
	@InjectMocks
	private GallerySystemService service;

	
	@BeforeEach
	public void setMockOutput() {

		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(shoppingCartDao.save(any(ShoppingCart.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(userDao.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
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
}
