package com.restaurantetech.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurantetech.inventory.model.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

	Optional<InventoryItem> findByDishId(Long dishId);
}
