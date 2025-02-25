package com.cloud.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDtoKafka {
    private Long userId;
    private Long orderId;
}
