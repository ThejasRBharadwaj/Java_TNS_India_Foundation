package com.example.fooddelivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name="food_items")
public class FoodItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=100) private String name;
    @Column(nullable=false) private double price;
    @Column(nullable=false) private boolean available=true;
    protected FoodItem() {}
    public FoodItem(String name,double price){this.name=name;this.price=price;this.available=true;}
    public Long getId(){return id;} public String getName(){return name;} public double getPrice(){return price;} public boolean isAvailable(){return available;}
    public void setName(String v){name=v;} public void setPrice(double v){price=v;} public void setAvailable(boolean v){available=v;}
}
