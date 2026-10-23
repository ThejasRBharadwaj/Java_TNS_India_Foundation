package com.example.fooddelivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cart_items", uniqueConstraints=@UniqueConstraint(columnNames={"customer_id","food_item_id"}))
public class CartItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false, fetch=FetchType.EAGER) @JoinColumn(name="customer_id") private Customer customer;
    @ManyToOne(optional=false, fetch=FetchType.EAGER) @JoinColumn(name="food_item_id") private FoodItem foodItem;
    @Column(nullable=false) private int quantity;
    protected CartItem() {}
    public CartItem(Customer c,FoodItem f,int q){customer=c;foodItem=f;quantity=q;}
    public Long getId(){return id;} public Customer getCustomer(){return customer;} public FoodItem getFoodItem(){return foodItem;} public int getQuantity(){return quantity;} public void setQuantity(int q){quantity=q;}
}
