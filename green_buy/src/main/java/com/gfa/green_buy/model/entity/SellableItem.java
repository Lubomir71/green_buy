package com.gfa.green_buy.model.entity;

import com.gfa.green_buy.model.dto.SellableItemDTO;
import jakarta.persistence.*;

@Entity
public class SellableItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    private String photoUrl;
    private Integer startingPrice;
    private Integer purchasePrice;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public SellableItem() {
    }

    public SellableItem(SellableItemDTO sellableItemDTO,User user){
        this.name = sellableItemDTO.getName();
        this.description = sellableItemDTO.getDescription();
        this.photoUrl = sellableItemDTO.getPhotoUrl();
        this.startingPrice = sellableItemDTO.getStartingPrice();
        this.purchasePrice = sellableItemDTO.getPurchasePrice();
        this.user =user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Integer startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
