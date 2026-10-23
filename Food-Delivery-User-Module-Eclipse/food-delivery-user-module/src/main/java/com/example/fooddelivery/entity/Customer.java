package com.example.fooddelivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name="customers")
public class Customer extends User {
    protected Customer() {}
    public Customer(String username, String contactNo, String passwordHash) { super(username, contactNo, passwordHash); }
}
