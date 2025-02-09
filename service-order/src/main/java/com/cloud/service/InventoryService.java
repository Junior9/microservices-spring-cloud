package com.cloud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final RestTemplate restTemplate;
    private final String URL = "http://SERVICE-INVERTORY/api/inventory/";

    public boolean hasProductQuantity(Long productId, int quantity){
        ResponseApi respobnseApi = this.restTemplate.getForObject(URL+"has/"+productId+"/"+quantity, ResponseApi.class);
        return respobnseApi.getMessage().equals("Success") && (Boolean) respobnseApi.getData();
    }

    public boolean decreaseInventoryProductQuantity(Long productId, int quantity){
        try {
            this.restTemplate.put(URL+"decrease/"+productId+"/"+quantity,"");
            return true;
        } catch (Exception error) {
            return false;
        }
    }
}
