package com.example.fooddelivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long userId;
    @Column(nullable=false, length=80) private String username;
    @Column(nullable=false, unique=true, length=20) private String contactNo;
    @Column(nullable=false) private String passwordHash;
    protected User() {}
    public User(String username, String contactNo, String passwordHash) { this.username=username; this.contactNo=contactNo; this.passwordHash=passwordHash; }
    public Long getUserId(){return userId;} public String getUsername(){return username;} public String getContactNo(){return contactNo;} public String getPasswordHash(){return passwordHash;}
    public void setUsername(String v){username=v;} public void setContactNo(String v){contactNo=v;} public void setPasswordHash(String v){passwordHash=v;}
}
