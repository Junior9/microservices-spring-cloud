package com.cloud.service;

import java.util.List;
import java.util.Optional;

import com.cloud.model.ProductInventory;

public interface IInventoreyService {

    public Optional<Boolean> addProductInventory(Long productId, int quantity);
    public Optional<Boolean> hasProduct(Long productId, int quantity);
    public Optional<Boolean> increaseProductInventory(Long productId, int quantityToIncrease); 
    public Optional<Boolean> decreaseProductInventory(Long productId, int quantityToDecrease); 
    public void deleteProductInventory(Long productId); 
    public Optional<List<ProductInventory>> getAll();

}
