package com.example.fooddelivery.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record PlaceOrderRequest(@NotBlank @Size(max=300) String deliveryAddress) {}
