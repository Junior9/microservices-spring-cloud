package com.cloud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.exceptions.InventoryException;
import com.cloud.response.ResponseApi;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final RestTemplate restTemplate;
    private final String URL = "http://SERVICE-INVERTORY/api/inventory/";


    @CircuitBreaker(name = "inventory-service", fallbackMethod = "inventoryOutService")
    @Retry(name = "inventory-service", fallbackMethod = "inventoryOutService")
    public boolean hasProductQuantity(Long productId, int quantity){
        ResponseApi respobnseApi = this.restTemplate.getForObject(URL+"has/"+productId+"/"+quantity, ResponseApi.class);
        return respobnseApi.getMessage().equals("Success") && (Boolean) respobnseApi.getData();
    }


    @CircuitBreaker(name = "inventory-service", fallbackMethod = "inventoryOutService")
    @Retry(name = "inventory-service", fallbackMethod = "inventoryOutService")
    public boolean decreaseInventoryProductQuantity(Long productId, int quantity){
        try {
            this.restTemplate.put(URL+"decrease/"+productId+"/"+quantity,"");
            return true;
        } catch (Exception error) {
            return false;
        }
    }

    private void inventoryOutService(Exception error) {
        throw new InventoryException("Inventory service out : " +error.getMessage());
    }

}
