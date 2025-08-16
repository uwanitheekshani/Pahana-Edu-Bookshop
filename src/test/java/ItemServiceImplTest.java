import com.example.pahanabillingsystem.dao.ItemDAO;
import com.example.pahanabillingsystem.model.Item;
import com.example.pahanabillingsystem.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ItemServiceImplTest {

    private ItemServiceImpl itemService;
    private ItemDAO itemDAO;

    @BeforeEach
    public void setUp() {
        itemDAO = Mockito.mock(ItemDAO.class);
        itemService = new ItemServiceImpl();
        itemService.setItemDAO(itemDAO);
    }

    @Test
    public void testAddItem() {
        Item item = new Item();
        item.setItemName("Book");
        item.setDescription("Java Programming");
        item.setUnitPrice(500.0);
        item.setQuantity(10);

        itemService.addItem(item);

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemDAO, times(1)).addItem(captor.capture());

        Item captured = captor.getValue();
        assertEquals("Book", captured.getItemName());
        assertEquals("Java Programming", captured.getDescription());
        assertEquals(500.0, captured.getUnitPrice());
        assertEquals(10, captured.getQuantity());
    }

    @Test
    public void testUpdateItem() {
        Item item = new Item();
        item.setId(1);
        item.setItemName("Updated Book");
        item.setDescription("Advanced Java");
        item.setUnitPrice(600.0);
        item.setQuantity(5);

        itemService.updateItem(item);

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemDAO, times(1)).updateItem(captor.capture());

        Item captured = captor.getValue();
        assertEquals(1, captured.getId());
        assertEquals("Updated Book", captured.getItemName());
        assertEquals("Advanced Java", captured.getDescription());
        assertEquals(600.0, captured.getUnitPrice());
        assertEquals(5, captured.getQuantity());
    }

    @Test
    public void testDeleteItem() {
        itemService.deleteItem(1);
        verify(itemDAO, times(1)).deleteItem(1);
    }

    @Test
    public void testGetItemById() {
        Item mockItem = new Item();
        mockItem.setId(1);
        mockItem.setItemName("Book");

        when(itemDAO.getItemById(1)).thenReturn(mockItem);

        Item result = itemService.getItemById(1);
        assertNotNull(result);
        assertEquals("Book", result.getItemName());
    }

    @Test
    public void testGetAllItems() {
        List<Item> mockItems = Arrays.asList(new Item(), new Item());
        when(itemDAO.getAllItems()).thenReturn(mockItems);

        List<Item> result = itemService.getAllItems();
        assertEquals(2, result.size());
    }
}