package com.cloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public Optional<List<Order>> findByUserId(Long id);

}
