package service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cloud.model.ProductInventory;
import com.cloud.repository.InventoryRepository;
import com.cloud.service.InventoryService;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Test
    public void addNewProductInventory(){

        Long id = 1L;
        int quantity = 10; 
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(quantity);

        when(this.inventoryRepository.save(Mockito.any())).thenReturn(productInventory);

        Optional<Boolean> response = this.inventoryService.addProductInventory(id, quantity);
        
        Assertions.assertTrue(response.isPresent());
        Assertions.assertTrue(response.get());
    }

    @Test
    public void addExistProductInventory(){

        Long id = 1L;
        int quantity = 5; 
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(quantity);

        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));
        when(this.inventoryRepository.save(Mockito.any())).thenReturn(productInventory);

        Optional<Boolean> response = this.inventoryService.addProductInventory(id, quantity);
        
        Assertions.assertTrue(response.isPresent());
        Assertions.assertTrue(response.get());

    }

    @Test
    public void hasProduct(){
        Long id = 1L;
        int quantity = 5;
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(6);

        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));

        Optional<Boolean> response = this.inventoryService.hasProduct(id, quantity);
        Assertions.assertTrue(response.isPresent());
        Assertions.assertTrue(response.get());
    }

    @Test
    public void noHasProduct(){
        Long id = 1L;
        int quantity = 5;
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(2);

        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));

        Optional<Boolean> response = this.inventoryService.hasProduct(id, quantity);
        Assertions.assertTrue(response.isPresent());
        Assertions.assertFalse(response.get());
    }

    @Test
    public void errorHasProductNotFound(){
        try {
            Long id = 1L;
            int quantity = 5;
            when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.empty());
            Optional<Boolean> response = this.inventoryService.hasProduct(id, quantity);
            Assertions.assertFalse(response.isPresent());
        } catch (Exception error) {
           Assertions.assertEquals("Error to get product inventory: -> Product not found productId 1", error.getMessage());
        }
    }

    @Test
    public void increaseQuantity(){
        Long id = 1L;
        int quantity = 2;
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(1);
        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));

        Optional<Boolean> response = this.inventoryService.increaseProductInventory(id, quantity);
        Assertions.assertTrue(response.isPresent());
        Assertions.assertTrue(response.get());
    }

    @Test
    public void errorIncreaseQuantityProductNotFound(){
        try {
            Long id = 1L;
            int quantity = 5;
            when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.empty());
            Optional<Boolean> response = this.inventoryService.increaseProductInventory(id, quantity);
            Assertions.assertFalse(response.isPresent());
        } catch (Exception error) {
           Assertions.assertEquals("Error to increase product inventory: -> Product not found productId 1", error.getMessage());
        }
    }

    @Test
    public void decreaseQuantity(){
        Long id = 1L;
        int quantity = 2;
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(6);
        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));

        Optional<Boolean> response = this.inventoryService.decreaseProductInventory(id, quantity);
        Assertions.assertTrue(response.isPresent());
        Assertions.assertTrue(response.get());
    }

    @Test
    public void decreaseQuantityIsNotEnough(){
        Long id = 1L;
        int quantity = 2;
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(id);
        productInventory.setQuantity(1);
        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));

        Optional<Boolean> response = this.inventoryService.decreaseProductInventory(id, quantity);
        Assertions.assertTrue(response.isPresent());
        Assertions.assertFalse(response.get());
    }

    @Test
    public void errorDecreaseQuantityProductNotFound(){
        try {
            Long id = 1L;
            int quantity = 5;
            when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.empty());
            Optional<Boolean> response = this.inventoryService.decreaseProductInventory(id, quantity);
            Assertions.assertFalse(response.isPresent());
        } catch (Exception error) {
           Assertions.assertEquals("Error to decrease product inventory: -> Product not found productId 1", error.getMessage());
        }
    }


    @Test
    public void delete(){
        Long id = 1L;
        int quantity = 2;
        ProductInventory productInventory = new ProductInventory();
        productInventory.setId(3L);
        productInventory.setProductId(id);
        productInventory.setQuantity(quantity);
        when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.of(productInventory));

        this.inventoryService.deleteProductInventory(id);

        verify(this.inventoryRepository, times(1)).deleteById(3L);
    }

    @Test
    public void deleteProductNotFound(){
        try {
            Long id = 1L;
            when(this.inventoryRepository.findByProductId(id)).thenReturn(Optional.empty());
            this.inventoryService.deleteProductInventory(id);
            verify(this.inventoryRepository,times(0)).deleteById(Mockito.anyLong());
        } catch (Exception error) {
            Assertions.assertEquals("Error to delete product inventory: -> Product not found productId 1", error.getMessage());
        }
    }

}
