package com.example.fooddelivery.repository;
import com.example.fooddelivery.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FoodItemRepository extends JpaRepository<FoodItem,Long> { List<FoodItem> findByAvailableTrue(); List<FoodItem> findByNameContainingIgnoreCaseAndAvailableTrue(String name); }
