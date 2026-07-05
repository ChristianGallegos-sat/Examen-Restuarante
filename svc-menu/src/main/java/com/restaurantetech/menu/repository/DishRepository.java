package com.restaurantetech.menu.repository;

import com.restaurantetech.menu.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
