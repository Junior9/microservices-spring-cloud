package com.cloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.cloud.model.Product;

public interface IProductService {
    public Optional<Product> add(Product product, int quantity);
    public Optional<Product> getById(Long id);
    public Optional<List<Product>> all();
    public Optional<Product> update(Product product, Long id);
    public void deleteById(Long productId);
    public Optional<Product> createImagineToProduct( MultipartFile file, Long productId);
}
