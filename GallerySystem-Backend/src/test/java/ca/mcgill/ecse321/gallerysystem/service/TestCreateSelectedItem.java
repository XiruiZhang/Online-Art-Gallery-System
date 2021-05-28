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

import ca.mcgill.ecse321.gallerysystem.dao.AdministratorRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ArtPieceRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ArtistRepository;
import ca.mcgill.ecse321.gallerysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.gallerysystem.dao.OrderRepository;
import ca.mcgill.ecse321.gallerysystem.dao.SelectedItemRepository;
import ca.mcgill.ecse321.gallerysystem.dao.ShoppingCartRepository;
import ca.mcgill.ecse321.gallerysystem.dao.UserRepository;
import ca.mcgill.ecse321.gallerysystem.model.ArtPiece;
import ca.mcgill.ecse321.gallerysystem.model.SelectedItem;


@ExtendWith(MockitoExtension.class)
public class TestCreateSelectedItem {
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
	
	
	@BeforeEach
	public void setMockOutput() {
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(artPieceDao.save(any(ArtPiece.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(selectedItemDao.save(any(SelectedItem.class))).thenAnswer(returnParameterAsAnswer);
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
}
