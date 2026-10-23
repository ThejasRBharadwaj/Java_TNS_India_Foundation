package com.example.fooddelivery.repository;
import com.example.fooddelivery.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CustomerRepository extends JpaRepository<Customer,Long> { Optional<Customer> findByContactNo(String contactNo); }
