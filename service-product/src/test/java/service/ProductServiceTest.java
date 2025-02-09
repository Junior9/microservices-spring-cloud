package service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.cloud.model.Imagine;
import com.cloud.model.Product;
import com.cloud.repository.ProductRepository;
import com.cloud.response.ResponseApi;
import com.cloud.service.InventoryService;
import com.cloud.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductService productService;

    @Test
    public void createProduct(){    
        Product product = new Product();
        int quantity = 10;
        product.setId(1L);
        
        when(this.productRepository.save(Mockito.any())).thenReturn(product);
        when(this.inventoryService.createProductInventory(1L, quantity)).thenReturn(ResponseApi.builder().message("Success").build());
        Optional<Product> productOp = this.productService.add(product, quantity);
        Assertions.assertTrue(productOp.isPresent());
    }

    @Test
    public void updateProduct(){    
        Product product = new Product();
        product.setId(1L);
        product.setName("Sofa");
        product.setDescription("Great Sofa");

        Product productOriginal = new Product();
        product.setId(1L);

        when(this.productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(productOriginal));
        when(this.productRepository.save(Mockito.any())).thenReturn(product);

        Optional<Product> productOp = this.productService.update(product, 1L);
        Assertions.assertTrue(productOp.isPresent());
    }

    @Test
    public void errorUpdateProductNotFound(){    
        try {
            Long id = 1L;
            Product product = new Product();
            product.setId(id);
            product.setName("Sofa");
            product.setDescription("Great Sofa");
            
            when(this.productRepository.findById(id)).thenReturn(Optional.empty());
        
            Optional<Product> productOp = this.productService.update(product, 1L);
            Assertions.assertFalse(productOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to update a product -> Product not found id 1", error.getMessage());
        }
    }

    @Test
    public void deleteProduct(){
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Sofa");
        product.setDescription("Great Sofa");
        
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));

        this.productService.deleteById(id);
        verify(this.productRepository).deleteById(id);
    }

    @Test
    public void errorDeleteProductNotFound(){
        try {
            Long id = 1L;
            when(this.productRepository.findById(id)).thenReturn(Optional.empty());
            this.productService.deleteById(id);
            verify(this.productRepository,times(0)).deleteById(id);
        } catch (Exception error) {
            Assertions.assertEquals("Error to delete product -> Product not found id 1", error.getMessage());
        }
    }

    @Test
    public void createImagineToProduct() throws SerialException, SQLException, IOException{
        Long id  = 1L;
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "hello.txt", 
            MediaType.TEXT_PLAIN_VALUE, 
            "Hello, World!".getBytes());
        
        List<Imagine> imagines = new ArrayList<>();
        Imagine img = new Imagine();
        img.setFileName(file.getName());
        img.setType(file.getContentType());
        img.setFile(new SerialBlob(file.getBytes()));
        imagines.add(img);
        
        Product product = new Product();
        product.setId(id);
        product.setName("Sofa");
        product.setDescription("Great Sofa");
        product.setImagines(imagines);
                
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));
        when(this.productRepository.save(Mockito.any())).thenReturn(product);

        Optional<Product> productOp = this.productService.createImagineToProduct(file, id);
        Assertions.assertTrue(productOp.isPresent());
        Assertions.assertEquals("Sofa", productOp.get().getName());
        Assertions.assertEquals(file.getName(), productOp.get().getImagines().get(0).getFileName());
    }

    @Test
    public void errorCreateImagineToProduct() throws SerialException, SQLException, IOException{
        try {
            Long id  = 1L;
            MockMultipartFile file = new MockMultipartFile(
                "file", 
                "hello.txt", 
                MediaType.TEXT_PLAIN_VALUE, 
                "Hello, World!".getBytes());
        
            when(this.productRepository.findById(id)).thenReturn(Optional.empty());

            Optional<Product> productOp = this.productService.createImagineToProduct(file, id);
            Assertions.assertFalse(productOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to add imagine to the product -> Product not found id 1", error.getMessage());
        }
    }

    @Test
    public void getById(){
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Sofa");
        product.setDescription("Great Sofa");

        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));
        
        Optional<Product> productOp = this.productService.getById(id);
        Assertions.assertTrue(productOp.isPresent());
    }

    @Test
    public void errorGetByIdProductNotFound(){
        try {
            Long id = 1L;
            when(this.productRepository.findById(id)).thenReturn(Optional.empty());
            Optional<Product> productOp = this.productService.getById(id);
            Assertions.assertFalse(productOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to get product -> Product not found id 1", error.getMessage());
        }
    }

}
