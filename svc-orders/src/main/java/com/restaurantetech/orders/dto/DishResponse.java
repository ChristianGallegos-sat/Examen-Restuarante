package com.restaurantetech.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponse {

	private Long id;
	private String name;
	private String description;
	private String category;
	private Double price;
	private Boolean available;
}
