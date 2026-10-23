package com.example.fooddelivery.controller;
import com.example.fooddelivery.entity.FoodItem;
import com.example.fooddelivery.repository.FoodItemRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/dev/food-items")
public class DevFoodController {
 private final FoodItemRepository repository;
 public DevFoodController(FoodItemRepository repository){this.repository=repository;}
 public record FoodRequest(@NotBlank @Size(max=100) String name,@DecimalMin(value="0.01") double price){}
 @PostMapping public ResponseEntity<FoodItem> create(@Valid @RequestBody FoodRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(new FoodItem(request.name().trim(),request.price())));}
}
