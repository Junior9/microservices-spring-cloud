package com.cloud.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.exceptions.DeleteProductException;
import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final RestTemplate restTemplate;

    public ResponseApi createProductInventory(Long productId, int quantity) {
        String url =  "http://SERVICE-INVERTORY/api/inventory/"+productId + "/" + quantity;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer SEU_TOKEN_AQUI");

        Map<String, Object> requestBody = new HashMap<>();

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ResponseApi> response = restTemplate.postForEntity(url, requestEntity, ResponseApi.class);
        return response.getBody();
    }

    public boolean deleteProductInventory(Long productId){
        try {
            String url =  "http://SERVICE-INVERTORY/api/inventory/delete/"+productId;
            this.restTemplate.delete(url);
            return  Boolean.TRUE;
        } catch (Exception e) {
            throw new DeleteProductException("Erro to delete product inventory");
        }
    }

}
