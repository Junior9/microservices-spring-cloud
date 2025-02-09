package com.cloud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.model.ProductInventory;

public interface InventoryRepository extends JpaRepository<ProductInventory, Long> {

    Optional<ProductInventory> findByProductId(Long productId);

}
