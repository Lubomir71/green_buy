package com.gfa.green_buy.model.entity;

import jakarta.persistence.*;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer offer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sellable_item_id")
    private SellableItem sellableItem;

    public Bid() {
    }

    public Bid(Integer offer, User user, SellableItem sellableItem) {
        this.offer = offer;
        this.user = user;
        this.sellableItem = sellableItem;
    }

    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public SellableItem getSellableItem() {
        return sellableItem;
    }

    public void setSellableItem(SellableItem sellableItem) {
        this.sellableItem = sellableItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }



}
