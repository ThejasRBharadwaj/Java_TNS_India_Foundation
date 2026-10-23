package com.example.fooddelivery.repository;
import com.example.fooddelivery.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CartItemRepository extends JpaRepository<CartItem,Long> { List<CartItem> findByCustomerUserId(Long customerId); Optional<CartItem> findByCustomerUserIdAndFoodItemId(Long customerId,Long foodId); void deleteByCustomerUserId(Long customerId); }
