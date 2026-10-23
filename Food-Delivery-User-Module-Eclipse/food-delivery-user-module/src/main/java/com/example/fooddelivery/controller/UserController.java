package com.example.fooddelivery.controller;
import com.example.fooddelivery.dto.*;
import com.example.fooddelivery.entity.FoodItem;
import com.example.fooddelivery.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api")
public class UserController {
 private final CustomerService service;
 public UserController(CustomerService service){this.service=service;}
 @PostMapping("/users/register") public ResponseEntity<Map<String,Object>> register(@Valid @RequestBody RegisterRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(service.register(request));}
 @PostMapping("/users/login") public Map<String,Object> login(@Valid @RequestBody LoginRequest request){return service.login(request);}
 @PostMapping("/users/logout") public Map<String,String> logout(){return Map.of("message","Logout acknowledged. This demo API is stateless; no server session was created.");}
 @GetMapping("/users/{userId}") public Map<String,Object> getUser(@PathVariable Long userId){return service.customerView(service.customer(userId));}
 @GetMapping("/food-items") public List<FoodItem> foods(@RequestParam(required=false) String search){return service.listFoods(search);}
 @GetMapping("/users/{userId}/cart") public Map<String,Object> getCart(@PathVariable Long userId){return service.cartView(userId);}
 @PostMapping("/users/{userId}/cart/items") public Map<String,Object> addToCart(@PathVariable Long userId,@Valid @RequestBody AddCartRequest request){return service.addToCart(userId,request);}
 @PutMapping("/users/{userId}/cart/items/{foodItemId}") public Map<String,Object> updateCart(@PathVariable Long userId,@PathVariable Long foodItemId,@Valid @RequestBody QuantityRequest request){return service.updateCart(userId,foodItemId,request.quantity());}
 @DeleteMapping("/users/{userId}/cart/items/{foodItemId}") public Map<String,Object> removeCartItem(@PathVariable Long userId,@PathVariable Long foodItemId){return service.removeFromCart(userId,foodItemId);}
 @PostMapping("/users/{userId}/orders") public ResponseEntity<Map<String,Object>> placeOrder(@PathVariable Long userId,@Valid @RequestBody PlaceOrderRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(service.placeOrder(userId,request));}
 @GetMapping("/users/{userId}/orders") public List<Map<String,Object>> orders(@PathVariable Long userId){return service.getOrders(userId);}
 @GetMapping("/users/{userId}/orders/{orderId}") public Map<String,Object> order(@PathVariable Long userId,@PathVariable Long orderId){return service.getOrder(userId,orderId);}
 @PatchMapping("/users/{userId}/orders/{orderId}/cancel") public Map<String,Object> cancel(@PathVariable Long userId,@PathVariable Long orderId){return service.cancelOrder(userId,orderId);}
}
