package com.restaurantetech.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantetech.inventory.client.MenuServiceClient;
import com.restaurantetech.inventory.exception.BadRequestException;
import com.restaurantetech.inventory.exception.ResourceNotFoundException;
import com.restaurantetech.inventory.model.InventoryItem;
import com.restaurantetech.inventory.repository.InventoryItemRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Autowired
	private MenuServiceClient menuServiceClient;

	public InventoryItem create(InventoryItem item) {
		menuServiceClient.getDish(item.getDishId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"El plato con id " + item.getDishId() + " no existe en el menú."));
		return inventoryItemRepository.save(item);
	}

	public List<InventoryItem> findAll() {
		return inventoryItemRepository.findAll();
	}

	public InventoryItem findByDishId(Long dishId) {
		return inventoryItemRepository.findByDishId(dishId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"No existe registro de inventario para el plato con id " + dishId + "."));
	}

	public InventoryItem decrementStock(Long dishId, Integer quantity) {
		InventoryItem item = findByDishId(dishId);
		if (item.getStockQuantity() < quantity) {
			throw new BadRequestException(
					"Stock insuficiente para el plato con id " + dishId
							+ ". Disponible: " + item.getStockQuantity());
		}
		item.setStockQuantity(item.getStockQuantity() - quantity);
		return inventoryItemRepository.save(item);
	}
}
