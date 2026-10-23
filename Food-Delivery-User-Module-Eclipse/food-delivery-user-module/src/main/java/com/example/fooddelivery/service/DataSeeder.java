package com.example.fooddelivery.service;
import com.example.fooddelivery.entity.FoodItem;
import com.example.fooddelivery.repository.FoodItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
@Configuration
public class DataSeeder {
 @Bean CommandLineRunner seedFood(FoodItemRepository foods){return args->{if(foods.count()==0)foods.saveAll(List.of(new FoodItem("Veg Burger",99.0),new FoodItem("Margherita Pizza",249.0),new FoodItem("Masala Dosa",80.0),new FoodItem("Paneer Biryani",189.0),new FoodItem("Cold Coffee",75.0)));};}
}
