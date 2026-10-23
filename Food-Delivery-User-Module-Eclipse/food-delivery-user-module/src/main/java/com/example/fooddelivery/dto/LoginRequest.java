package com.example.fooddelivery.dto;
import jakarta.validation.constraints.NotBlank;
public record LoginRequest(@NotBlank String contactNo,@NotBlank String password) {}
