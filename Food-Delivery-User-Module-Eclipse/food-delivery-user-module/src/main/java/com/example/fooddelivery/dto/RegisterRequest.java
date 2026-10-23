package com.example.fooddelivery.dto;
import jakarta.validation.constraints.*;
public record RegisterRequest(@NotBlank @Size(max=80) String username, @NotBlank @Pattern(regexp="[0-9+() -]{7,20}",message="Contact number must contain 7-20 digits or phone symbols") String contactNo, @NotBlank @Size(min=8,max=72) String password) {}
