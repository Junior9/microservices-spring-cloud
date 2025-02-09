package com.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
public class ProductOrder {

    private Long productId;
    private int quantity;
}
