package com.example.fooddelivery.repository;
import com.example.fooddelivery.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> { List<FoodOrder> findByCustomerUserIdOrderByCreatedAtDesc(Long customerId); Optional<FoodOrder> findByOrderIdAndCustomerUserId(Long orderId,Long customerId); }
