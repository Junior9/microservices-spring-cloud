package com.cloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cloud.exceptions.CreateProductException;
import com.cloud.exceptions.DeleteProductexception;
import com.cloud.exceptions.GetProductException;
import com.cloud.exceptions.ProductNoFoundException;
import com.cloud.exceptions.UpdateProductException;
import com.cloud.model.ProductInventory;
import com.cloud.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoreyService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Optional<Boolean> addProductInventory(Long productId, int quantity) {
       
        try {
            Optional<ProductInventory> productInOp =  this.inventoryRepository.findByProductId(productId);
            if(productInOp.isPresent()){
                ProductInventory productInventoryExist = productInOp.get();
                productInventoryExist.setQuantity(productInventoryExist.getQuantity() + quantity);
                this.inventoryRepository.save(productInventoryExist);
                return Optional.of(Boolean.TRUE);
            }else{
                ProductInventory productInventory = new ProductInventory();
                productInventory.setProductId(productId);
                productInventory.setQuantity(quantity);
                this.inventoryRepository.save(productInventory);
                return Optional.of(Boolean.TRUE);
            }
        } catch (Exception error) {
            throw new CreateProductException("Error to create product inventory: " + error.getMessage());
        }
    }

    @Override
    public Optional<Boolean> hasProduct(Long productId, int quantity) {
        try {
            ProductInventory productInventory = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(()-> new ProductNoFoundException("-> Product not found productId " + productId));

            if(productInventory.getQuantity() >= quantity){
                return Optional.of(Boolean.TRUE);
            }
            return Optional.of(Boolean.FALSE);
        } catch (Exception error) {
            throw new GetProductException("Error to get product inventory: " + error.getMessage());
        }
    }

    @Override
    public Optional<Boolean> increaseProductInventory(Long productId, int quantityToIncrease) {   
        try {
            ProductInventory productInventory = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(()-> new ProductNoFoundException("-> Product not found productId " + productId));
            
            productInventory.setQuantity(productInventory.getQuantity() + quantityToIncrease);
            this.inventoryRepository.save(productInventory);
            return Optional.of(Boolean.TRUE);
        } catch (Exception error) {
            throw new UpdateProductException("Error to increase product inventory: " + error.getMessage());
        }
    }

    @Override
    public Optional<Boolean> decreaseProductInventory(Long productId, int quantityToDecrease) {
        try {
            ProductInventory productInventory = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(()-> new ProductNoFoundException("-> Product not found productId " + productId));

            if(productInventory.getQuantity() >= quantityToDecrease){
                productInventory.setQuantity(productInventory.getQuantity() - quantityToDecrease);
                this.inventoryRepository.save(productInventory);
                return Optional.of(Boolean.TRUE);
            }
            return Optional.of(Boolean.FALSE);
        } catch (Exception error) {
            throw new UpdateProductException("Error to decrease product inventory: " + error.getMessage());
        }
    }

    @Override
    public void deleteProductInventory(Long productId) {
        try {
            ProductInventory productInventory = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(()-> new ProductNoFoundException("-> Product not found productId " + productId));
            this.inventoryRepository.deleteById(productInventory.getId());
        } catch (Exception error) {
           throw new DeleteProductexception("Error to delete product inventory: " + error.getMessage());
        }
    }

    @Override
    public Optional<List<ProductInventory>> getAll() {
        try {
            List<ProductInventory> products = this.inventoryRepository.findAll();
            return Optional.of(products);
        } catch (Exception error) {
            throw new GetProductException("Error to get product inventory: " + error.getMessage());
        }
    }

}
