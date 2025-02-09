package com.cloud.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.exceptions.ConvertDtoException;
import com.cloud.model.Product;
import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final String URL = "http://SERVICE-PRODUCT/api/product/";

    public Product getProduct(Long id){
        try {
            ResponseApi respobnseApi = this.restTemplate.getForObject(URL+id, ResponseApi.class);
            if(respobnseApi.getMessage().equals("Success")){
                LinkedHashMap map = (LinkedHashMap) respobnseApi.getData();
                Product product = new Product();
                Integer idInteger = (Integer)  map.get("id");
                Double priceDouble = (Double) map.get("price");
                product.setId(Long.parseLong(idInteger.toString()));
                product.setName((String) map.get("name"));  
                product.setPrice(BigDecimal.valueOf(priceDouble));
                return  product;
            }else{
                throw new ConvertDtoException("Product not foud id " + id );
            }
        } catch (Exception error) {
            throw new ConvertDtoException("Error to get product " + error.getMessage());
        }
    }


}
