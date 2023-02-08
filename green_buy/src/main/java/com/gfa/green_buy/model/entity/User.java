package com.gfa.green_buy.model.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Set;

@Entity
@Table(name= "green_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column (unique = true)
    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "user")
    Set<Bid> bids;

    @OneToMany(mappedBy = "user")
    Set<SellableItem> sellableItems;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Set<SellableItem> getSellableItems() {
        return sellableItems;
    }

    public void setSellableItems(Set<SellableItem> sellableItems) {
        this.sellableItems = sellableItems;
    }
}
