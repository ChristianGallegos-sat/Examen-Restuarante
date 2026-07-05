package com.restaurantetech.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantetech.inventory.model.InventoryItem;
import com.restaurantetech.inventory.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryItem create(@Valid @RequestBody InventoryItem item) {
		item.setId(null);
		return inventoryService.create(item);
	}

	@GetMapping
	public List<InventoryItem> findAll() {
		return inventoryService.findAll();
	}

	@GetMapping("/{dishId}")
	public InventoryItem findByDishId(@PathVariable Long dishId) {
		return inventoryService.findByDishId(dishId);
	}

	@PutMapping("/{dishId}/decrement")
	public InventoryItem decrement(@PathVariable Long dishId, @RequestParam Integer quantity) {
		return inventoryService.decrementStock(dishId, quantity);
	}
}
