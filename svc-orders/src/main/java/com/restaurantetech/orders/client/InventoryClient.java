package com.restaurantetech.orders.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.restaurantetech.orders.dto.InventoryResponse;

@Component
public class InventoryClient {

	private final WebClient webClient;

	public InventoryClient(WebClient.Builder webClientBuilder,
			@Value("${inventory.service.url}") String inventoryServiceUrl) {
		this.webClient = webClientBuilder.baseUrl(inventoryServiceUrl).build();
	}

	public Optional<InventoryResponse> getInventory(Long dishId) {
		try {
			InventoryResponse inventory = webClient.get()
					.uri("/api/inventory/{dishId}", dishId)
					.retrieve()
					.bodyToMono(InventoryResponse.class)
					.block();
			return Optional.ofNullable(inventory);
		} catch (WebClientResponseException.NotFound ex) {
			return Optional.empty();
		}
	}

	public void decrementStock(Long dishId, Integer quantity) {
		webClient.put()
				.uri("/api/inventory/{dishId}/decrement?quantity={quantity}", dishId, quantity)
				.retrieve()
				.toBodilessEntity()
				.block();
	}
}
