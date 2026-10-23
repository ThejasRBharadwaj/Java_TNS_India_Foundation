package com.example.fooddelivery.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public record AddCartRequest(@NotNull Long foodItemId,@Min(1) int quantity) {}
