package com.restaurantetech.inventory.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InventoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long dishId;

	@NotNull
	@Min(0)
	private Integer stockQuantity;

	private LocalDateTime lastUpdated;

	@PrePersist
	@PreUpdate
	protected void onSave() {
		this.lastUpdated = LocalDateTime.now();
	}
}
