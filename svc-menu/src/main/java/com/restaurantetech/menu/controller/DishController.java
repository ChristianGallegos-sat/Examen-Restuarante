package com.restaurantetech.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantetech.menu.model.Dish;
import com.restaurantetech.menu.service.DishService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/menu/dishes")
public class DishController {

	@Autowired
	private DishService dishService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Dish create(@Valid @RequestBody Dish dish) {
		dish.setId(null);
		return dishService.create(dish);
	}

	@GetMapping
	public List<Dish> findAll() {
		return dishService.findAll();
	}

	@GetMapping("/{id}")
	public Dish findById(@PathVariable Long id) {
		return dishService.findById(id);
	}

	@PutMapping("/{id}")
	public Dish update(@PathVariable Long id, @Valid @RequestBody Dish dish) {
		return dishService.update(id, dish);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		dishService.delete(id);
	}
}
