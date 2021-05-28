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
import ca.mcgill.ecse321.gallerysystem.dao.UserRepository;
import ca.mcgill.ecse321.gallerysystem.model.Customer;
import ca.mcgill.ecse321.gallerysystem.model.User;

@ExtendWith(MockitoExtension.class)
public class TestCreateCustomer {
	@Mock
	private CustomerRepository customerDao;
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
		lenient().when(userDao.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
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
}
