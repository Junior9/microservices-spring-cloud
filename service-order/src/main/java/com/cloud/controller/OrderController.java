package com.cloud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dto.OrderDto;
import com.cloud.enums.StatusOrder;
import com.cloud.model.Order;
import com.cloud.response.ResponseApi;
import com.cloud.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ResponseApi> create(@RequestBody OrderDto orderDto){
        try {
            Optional<Order> orderOp = this.orderService.create(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.builder().message("Success").data(orderOp.get()).build());

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseApi.builder().message("Error to create order " + error.getMessage()).build());
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseApi> update(@PathVariable StatusOrder statusOrder, @PathVariable Long id){
        try {
            Optional<Order> orderOp = this.orderService.updateStatus(id, statusOrder);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.builder().message("Success").data(orderOp.get()).build());

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseApi.builder().message("Error to update order " + error.getMessage()).build());
        }
    }


    @GetMapping("id/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Long id){
        try {
            Optional<Order> ordersOp = this.orderService.getOrdersById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.builder().message("Success").data(ordersOp.get()).build());

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseApi.builder().message("Error to get order by id :" + error.getMessage()).build());
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ResponseApi> getByUserId(@PathVariable Long userId){
        try {
            Optional<List<Order>> ordersOp = this.orderService.getOrdersByUserId(userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.builder().message("Success").data(ordersOp.get()).build());

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseApi.builder().message("Error to get orders by user id :" + error.getMessage()).build());
        }
    }

    @GetMapping("all/orders")
    public ResponseEntity<ResponseApi> getAll(){
        try {
            Optional<List<Order>> ordersOp = this.orderService.all();
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseApi.builder().message("Success").data(ordersOp.get()).build());

        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseApi.builder().message("Error to get all orders " + error.getMessage()).build());
        }
    }

}
