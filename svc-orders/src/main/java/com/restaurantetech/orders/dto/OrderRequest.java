package com.restaurantetech.orders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

	@NotNull
	@Size(max = 100)
	private String customerName;

	@NotNull
	private Long dishId;

	@NotNull
	@Min(1)
	private Integer quantity;
}
