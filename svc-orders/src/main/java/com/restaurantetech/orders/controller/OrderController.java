package com.restaurantetech.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantetech.orders.dto.OrderRequest;
import com.restaurantetech.orders.model.Order;
import com.restaurantetech.orders.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Order create(@Valid @RequestBody OrderRequest request) {
		return orderService.createOrder(request);
	}

	@GetMapping
	public List<Order> findAll() {
		return orderService.findAll();
	}

	@GetMapping("/{id}")
	public Order findById(@PathVariable Long id) {
		return orderService.findById(id);
	}

	@PutMapping("/{id}/confirm")
	public Order confirm(@PathVariable Long id) {
		return orderService.confirmOrder(id);
	}
}
