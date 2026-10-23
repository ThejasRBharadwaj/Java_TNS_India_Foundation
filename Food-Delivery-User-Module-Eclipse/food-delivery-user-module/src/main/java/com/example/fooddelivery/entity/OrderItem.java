package com.example.fooddelivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name="order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="order_id") private FoodOrder order;
    @ManyToOne(optional=false) @JoinColumn(name="food_item_id") private FoodItem foodItem;
    @Column(nullable=false) private int quantity;
    @Column(nullable=false) private double unitPrice;
    protected OrderItem() {}
    public OrderItem(FoodItem f,int q,double p){foodItem=f;quantity=q;unitPrice=p;}
    public void setOrder(FoodOrder o){order=o;} public FoodItem getFoodItem(){return foodItem;} public int getQuantity(){return quantity;} public double getUnitPrice(){return unitPrice;}
}
