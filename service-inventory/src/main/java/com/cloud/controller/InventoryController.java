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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.ProductInventory;
import com.cloud.response.ResponseApi;
import com.cloud.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory/")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("{productId}/{quantity}")
    public ResponseEntity<ResponseApi> create(@PathVariable Long productId,@PathVariable int quantity){
        try {
            this.inventoryService.addProductInventory(productId, quantity);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(Boolean.TRUE).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error create product ->" + error.getMessage()).build());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseApi> all(){
        try {
            Optional<List<ProductInventory>> productsInventories = this.inventoryService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(productsInventories.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error has product ->" + error.getMessage()).build());
        }
    }

    @GetMapping("/has/{productId}/{quantity}")
    public ResponseEntity<ResponseApi> hasProduct(@PathVariable Long productId,@PathVariable int quantity){
        try {
            Optional<Boolean> response = this.inventoryService.hasProduct(productId, quantity);
            Boolean boolResponse = response.get();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(boolResponse).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error has product ->" + error.getMessage()).build());
        }
    }

    @PutMapping("/increase/{productId}/{quantity}")
    public ResponseEntity<ResponseApi> increaseQuantity(@PathVariable Long productId,@PathVariable int quantity){
        try {
            Optional<Boolean> response = this.inventoryService.increaseProductInventory(productId, quantity);
            Boolean boolResponse = response.get();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(boolResponse).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error has product ->" + error.getMessage()).build());
        }
    }

    @PutMapping("/decrease/{productId}/{quantity}")
    public ResponseEntity<ResponseApi> decreaseQuantity(@PathVariable Long productId,@PathVariable int quantity){
        try {
            Optional<Boolean> response = this.inventoryService.decreaseProductInventory(productId, quantity);
            Boolean boolResponse = response.get();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(boolResponse).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error has product ->" + error.getMessage()).build());
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ResponseApi> delete(@PathVariable Long productId){
        try {
            this.inventoryService.deleteProductInventory(productId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(Boolean.TRUE).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error has product ->" + error.getMessage()).build());
        }
    }
}
