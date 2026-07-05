package com.restaurantetech.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {

	private Long id;
	private Long dishId;
	private Integer stockQuantity;
}
