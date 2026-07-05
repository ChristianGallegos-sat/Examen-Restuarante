package com.restaurantetech.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantetech.orders.client.InventoryClient;
import com.restaurantetech.orders.client.MenuServiceClient;
import com.restaurantetech.orders.dto.DishResponse;
import com.restaurantetech.orders.dto.InventoryResponse;
import com.restaurantetech.orders.dto.OrderRequest;
import com.restaurantetech.orders.exception.BadRequestException;
import com.restaurantetech.orders.exception.ResourceNotFoundException;
import com.restaurantetech.orders.model.Order;
import com.restaurantetech.orders.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MenuServiceClient menuServiceClient;

	@Autowired
	private InventoryClient inventoryClient;

	public Order createOrder(OrderRequest request) {
		DishResponse dish = menuServiceClient.getDish(request.getDishId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"El plato con id " + request.getDishId() + " no existe en el menú."));

		if (!Boolean.TRUE.equals(dish.getAvailable())) {
			throw new BadRequestException(
					"El plato '" + dish.getName() + "' no está disponible actualmente.");
		}

		Order order = new Order();
		order.setCustomerName(request.getCustomerName());
		order.setDishId(request.getDishId());
		order.setQuantity(request.getQuantity());
		order.setTotal(dish.getPrice() * request.getQuantity());
		order.setStatus("PENDING");

		return orderRepository.save(order);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El pedido con id " + id + " no existe."));
	}

	public Order confirmOrder(Long id) {
		Order order = findById(id);

		InventoryResponse inventory = inventoryClient.getInventory(order.getDishId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"No existe registro de inventario para el plato con id " + order.getDishId() + "."));

		if (inventory.getStockQuantity() < order.getQuantity()) {
			throw new BadRequestException(
					"Stock insuficiente para el plato con id " + order.getDishId()
							+ ". Disponible: " + inventory.getStockQuantity());
		}

		inventoryClient.decrementStock(order.getDishId(), order.getQuantity());
		order.setStatus("CONFIRMED");
		return orderRepository.save(order);
	}
}
