package com.example.fooddelivery.dto;
import jakarta.validation.constraints.Min;
public record QuantityRequest(@Min(1) int quantity) {}
