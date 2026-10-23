package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.*;
import com.example.fooddelivery.entity.*;
import com.example.fooddelivery.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
 private final CustomerRepository customers; private final FoodItemRepository foods; private final CartItemRepository cart; private final FoodOrderRepository orders; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public CustomerService(CustomerRepository c,FoodItemRepository f,CartItemRepository ca,FoodOrderRepository o){customers=c;foods=f;cart=ca;orders=o;}
 public Map<String,Object> register(RegisterRequest r){if(customers.findByContactNo(r.contactNo()).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Contact number is already registered");Customer c=customers.save(new Customer(r.username().trim(),r.contactNo().trim(),encoder.encode(r.password())));return customerView(c);}
 public Map<String,Object> login(LoginRequest r){Customer c=customers.findByContactNo(r.contactNo()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid contact number or password"));if(!encoder.matches(r.password(),c.getPasswordHash()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid contact number or password");return Map.of("message","Login successful","customer",customerView(c));}
 public Map<String,Object> customerView(Customer c){return Map.of("userId",c.getUserId(),"username",c.getUsername(),"contactNo",c.getContactNo());}
 public Customer customer(Long id){return customers.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found"));}
 public List<FoodItem> listFoods(String search){if(search==null||search.isBlank())return foods.findByAvailableTrue();return foods.findByNameContainingIgnoreCaseAndAvailableTrue(search.trim());}
 public FoodItem food(Long id){FoodItem f=foods.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Food item not found"));if(!f.isAvailable())throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Food item is not available");return f;}
 @Transactional public Map<String,Object> addToCart(Long customerId,AddCartRequest r){Customer c=customer(customerId);FoodItem f=food(r.foodItemId());CartItem item=cart.findByCustomerUserIdAndFoodItemId(customerId,f.getId()).orElse(null);if(item==null)item=new CartItem(c,f,r.quantity());else item.setQuantity(item.getQuantity()+r.quantity());cart.save(item);return cartView(customerId);}
 @Transactional public Map<String,Object> updateCart(Long customerId,Long foodId,int quantity){customer(customerId);CartItem item=cart.findByCustomerUserIdAndFoodItemId(customerId,foodId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Item is not in the cart"));item.setQuantity(quantity);cart.save(item);return cartView(customerId);}
 @Transactional public Map<String,Object> removeFromCart(Long customerId,Long foodId){customer(customerId);CartItem item=cart.findByCustomerUserIdAndFoodItemId(customerId,foodId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Item is not in the cart"));cart.delete(item);return cartView(customerId);}
 public Map<String,Object> cartView(Long customerId){customer(customerId);List<CartItem> items=cart.findByCustomerUserId(customerId);List<Map<String,Object>> lines=items.stream().map(i->Map.<String,Object>of("foodItemId",i.getFoodItem().getId(),"name",i.getFoodItem().getName(),"unitPrice",i.getFoodItem().getPrice(),"quantity",i.getQuantity(),"subtotal",round(i.getFoodItem().getPrice()*i.getQuantity()))).collect(Collectors.toList());double total=items.stream().mapToDouble(i->i.getFoodItem().getPrice()*i.getQuantity()).sum();return Map.of("customerId",customerId,"items",lines,"total",round(total));}
 @Transactional public Map<String,Object> placeOrder(Long customerId,PlaceOrderRequest r){Customer c=customer(customerId);List<CartItem> lines=cart.findByCustomerUserId(customerId);if(lines.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cart is empty");FoodOrder order=new FoodOrder(c,r.deliveryAddress().trim());for(CartItem line:lines){if(!line.getFoodItem().isAvailable())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A cart item is no longer available");order.addItem(new OrderItem(line.getFoodItem(),line.getQuantity(),line.getFoodItem().getPrice()));}FoodOrder saved=orders.save(order);cart.deleteByCustomerUserId(customerId);return orderView(saved);}
 public List<Map<String,Object>> getOrders(Long customerId){customer(customerId);return orders.findByCustomerUserIdOrderByCreatedAtDesc(customerId).stream().map(this::orderView).toList();}
 public Map<String,Object> getOrder(Long customerId,Long orderId){customer(customerId);FoodOrder o=orders.findByOrderIdAndCustomerUserId(orderId,customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found for this customer"));return orderView(o);}
 @Transactional public Map<String,Object> cancelOrder(Long customerId,Long orderId){FoodOrder o=orders.findByOrderIdAndCustomerUserId(orderId,customerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found for this customer"));if(!"Pending".equals(o.getStatus()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Only pending orders can be cancelled");o.setStatus("Cancelled");orders.save(o);return orderView(o);}
 public Map<String,Object> orderView(FoodOrder o){List<Map<String,Object>> lines=o.getItems().stream().map(i->Map.<String,Object>of("foodItemId",i.getFoodItem().getId(),"name",i.getFoodItem().getName(),"unitPrice",i.getUnitPrice(),"quantity",i.getQuantity(),"subtotal",round(i.getUnitPrice()*i.getQuantity()))).toList();double total=o.getItems().stream().mapToDouble(i->i.getUnitPrice()*i.getQuantity()).sum();return Map.of("orderId",o.getOrderId(),"customerId",o.getCustomer().getUserId(),"items",lines,"total",round(total),"status",o.getStatus(),"deliveryAddress",o.getDeliveryAddress(),"createdAt",o.getCreatedAt().toString());}
 private double round(double v){return Math.round(v*100.0)/100.0;}
}
