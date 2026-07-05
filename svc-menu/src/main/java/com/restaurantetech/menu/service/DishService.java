package com.restaurantetech.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantetech.menu.exception.ResourceNotFoundException;
import com.restaurantetech.menu.model.Dish;
import com.restaurantetech.menu.repository.DishRepository;

@Service
public class DishService {

	@Autowired
	private DishRepository dishRepository;

	public Dish create(Dish dish) {
		return dishRepository.save(dish);
	}

	public List<Dish> findAll() {
		return dishRepository.findAll();
	}

	public Dish findById(Long id) {
		return dishRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El plato con id " + id + " no existe."));
	}

	public Dish update(Long id, Dish updatedDish) {
		Dish dish = findById(id);
		dish.setName(updatedDish.getName());
		dish.setDescription(updatedDish.getDescription());
		dish.setCategory(updatedDish.getCategory());
		dish.setPrice(updatedDish.getPrice());
		dish.setAvailable(updatedDish.getAvailable());
		return dishRepository.save(dish);
	}

	public void delete(Long id) {
		Dish dish = findById(id);
		dishRepository.delete(dish);
	}
}
