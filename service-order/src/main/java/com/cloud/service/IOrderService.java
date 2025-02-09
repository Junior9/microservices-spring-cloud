package com.cloud.service;

import java.util.List;
import java.util.Optional;

import com.cloud.dto.OrderDto;
import com.cloud.enums.StatusOrder;
import com.cloud.model.Order;

public interface IOrderService {
    
    public Optional<Order> create(OrderDto order);
    public Optional<Order> updateStatus(Long orderId, StatusOrder statusOrder);
    public Optional<List<Order>> getOrdersByUserId(Long id);
    public Optional<List<Order>> all();
    public Order convertDtoToOrder(OrderDto orderdto);
}
