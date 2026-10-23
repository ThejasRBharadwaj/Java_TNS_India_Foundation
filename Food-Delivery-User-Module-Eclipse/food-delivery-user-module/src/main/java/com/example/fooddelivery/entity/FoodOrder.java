package com.example.fooddelivery.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="food_orders")
public class FoodOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long orderId;
    @ManyToOne(optional=false) @JoinColumn(name="customer_id") private Customer customer;
    @Column(nullable=false) private String status="Pending";
    @Column(nullable=false) private String deliveryAddress;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER) private List<OrderItem> items=new ArrayList<>();
    protected FoodOrder() {}
    public FoodOrder(Customer c,String address){customer=c;deliveryAddress=address;status="Pending";createdAt=LocalDateTime.now();}
    public void addItem(OrderItem i){items.add(i);i.setOrder(this);}
    public Long getOrderId(){return orderId;} public Customer getCustomer(){return customer;} public String getStatus(){return status;} public void setStatus(String s){status=s;} public String getDeliveryAddress(){return deliveryAddress;} public LocalDateTime getCreatedAt(){return createdAt;} public List<OrderItem> getItems(){return items;}
}
