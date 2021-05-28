package ca.mcgill.ecse321.gallerysystem.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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

import ca.mcgill.ecse321.gallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.gallerysystem.dao.OrderRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ShoppingCartRepository;
import ca.mcgill.ecse321.gallerysystem.model.Customer;
import ca.mcgill.ecse321.gallerysystem.model.Order;
import ca.mcgill.ecse321.gallerysystem.model.ShoppingCart;

@ExtendWith(MockitoExtension.class)
public class TestCreateOrder {
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private OrderRepository orderDao;
	@Mock
	private ShoppingCartRepository shoppingCartDao;
	
	@InjectMocks
	private GallerySystemService service;
	
	
	@BeforeEach
	public void setMockOutput() {
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(orderDao.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(shoppingCartDao.save(any(ShoppingCart.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
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
}
