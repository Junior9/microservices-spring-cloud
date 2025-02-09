package com.cloud.service;

import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.exceptions.CreateProductException;
import com.cloud.exceptions.DeleteProductException;
import com.cloud.exceptions.GetProductException;
import com.cloud.exceptions.ProductNotFoundException;
import com.cloud.exceptions.UpdateProductException;
import com.cloud.model.Imagine;
import com.cloud.model.Product;
import com.cloud.repository.ProductRepository;
import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public Optional<Product> add(Product product, int quantity) {
        try {
            Product productAdded = this.productRepository.save(product);
            ResponseApi responseInventory = this.inventoryService.createProductInventory(productAdded.getId(), quantity);
            if(!responseInventory.getMessage().equals("Success")){
                throw new CreateProductException("Error to update inventory");
            }   
            return Optional.of(productAdded);
        } catch (Exception error) {
            throw new CreateProductException("Error to create a product " + error.getMessage());
        }
    }

    @Override
    public Optional<Product> update(Product product, Long id) {
        try {
            Product productOr = this.productRepository
                .findById(id)
                .orElseThrow(()-> new ProductNotFoundException("-> Product not found id " + id));
            this.updateProduct(product, productOr);
            Product productUpdated = this.productRepository.save(productOr);
            return Optional.of(productUpdated);
        } catch (Exception error) {
            throw new UpdateProductException("Error to update a product " + error.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long productId) {
        try {
            this.productRepository
                .findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("-> Product not found id " + productId));
            this.productRepository.deleteById(productId);
            this.inventoryService.deleteProductInventory(productId);
        } catch (Exception error) {
            throw new DeleteProductException("Error to delete product " + error.getMessage());
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        try {
            Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("-> Product not found id " + id));
            return Optional.of(product);
        } catch (Exception error) {
            throw new GetProductException("Error to get product " + error.getMessage());
        }
    }

    @Override
    public Optional<List<Product>> all() {
        try {
            List<Product> products = this.productRepository.findAll();
            return Optional.of(products);
        } catch (Exception error) {
            throw new GetProductException("Error to get all products");
        }
    }

    @Override
    public Optional<Product> createImagineToProduct(MultipartFile file, Long productId) {
        try {
            Product product = this.productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("-> Product not found id " + productId));
            
            Imagine imagine = new Imagine();
            imagine.setFileName(file.getOriginalFilename());
            imagine.setType(file.getContentType());
            imagine.setFile(new SerialBlob(file.getBytes()));
            product.getImagines().add(imagine);
            Product productUpdated = this.productRepository.save(product);
            return Optional.of(productUpdated);
        } catch (Exception error) {
            throw new GetProductException("Error to add imagine to the product " + error.getMessage());
        }
    }

    private void updateProduct(Product nProduct, Product oProduct){
        oProduct.setDescription(nProduct.getDescription());
        oProduct.setName(nProduct.getName());
        oProduct.setPrice(nProduct.getPrice());
    }

}
