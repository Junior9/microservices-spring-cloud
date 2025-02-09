package com.cloud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.model.Product;
import com.cloud.response.ResponseApi;
import com.cloud.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/")
public class ProductController {

    private final ProductService productService;

    @PostMapping("{quantity}")
    public ResponseEntity<ResponseApi> createProduct(@RequestBody Product product, @PathVariable int quantity){
        try {
            Optional<Product> productAdded = this.productService.add(product, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").data(productAdded).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to create product :" + error.getMessage()).build());
        }
    }

    @PostMapping("imagine")
    public ResponseEntity<ResponseApi> createImagineToProduct(@RequestBody MultipartFile file, @PathVariable Long productId ) {
        try {
            Optional<Product> productAdded = this.productService.createImagineToProduct(file, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").data(productAdded).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to add imagine to product :" + error.getMessage()).build());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseApi> updateProduct(@RequestBody Product product, @PathVariable Long id){
        try {
            Optional<Product> productAdded = this.productService.update(product, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").data(productAdded).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to update product :" + error.getMessage()).build());
        }
    }

    @GetMapping("all/products")
    public ResponseEntity<ResponseApi> getAll(){
        try {
            Optional<List<Product>> products = this.productService.all();
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").data(products).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to get all products :" + error.getMessage()).build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Long id){
        try {
            Optional<Product> product = this.productService.getById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").data(product).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to get product :" + error.getMessage()).build());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Long id){
        try {
            this.productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to delete product :" + error.getMessage()).build());
        }
    }

}
