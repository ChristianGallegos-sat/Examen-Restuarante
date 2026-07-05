package com.restaurantetech.inventory.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class MenuServiceClient {

	private final WebClient webClient;

	public MenuServiceClient(WebClient.Builder webClientBuilder,
			@Value("${menu.service.url}") String menuServiceUrl) {
		this.webClient = webClientBuilder.baseUrl(menuServiceUrl).build();
	}

	public Optional<DishResponse> getDish(Long dishId) {
		try {
			DishResponse dish = webClient.get()
					.uri("/api/menu/dishes/{id}", dishId)
					.retrieve()
					.bodyToMono(DishResponse.class)
					.block();
			return Optional.ofNullable(dish);
		} catch (WebClientResponseException.NotFound ex) {
			return Optional.empty();
		}
	}
}
