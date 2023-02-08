package com.gfa.green_buy.model.dto;

import com.gfa.green_buy.model.entity.SellableItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public class SellableItemDTO {

    private Long id;
    @NotBlank(message = "Given name is null or empty!")
    private String name;
    @NotBlank(message = "Given description is null or empty!")
    private String description;
    @NotBlank(message = "Given photo url is null or empty!")
    @URL(message = "Given photo url is not valid url!")
    private String photoUrl;
    @NotNull(message = "Given starting price is null or empty!")
    @Min(value = 0, message = "Given starting price must be a positive number")
    private Integer startingPrice;
    @Min(value = 0, message = "Given purchase price must be a positive number")
    @NotNull(message = "Given purchase price is null or empty!")
    private Integer purchasePrice;

    public SellableItemDTO() {
    }

    public SellableItemDTO(Long id, String name, String description, String photoUrl, Integer startingPrice, Integer purchasePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.startingPrice = startingPrice;
        this.purchasePrice = purchasePrice;
    }

    public SellableItemDTO(SellableItem sellableItem) {
        this.id = sellableItem.getId();
        this.name = sellableItem.getName();
        this.description = sellableItem.getDescription();
        this.photoUrl = sellableItem.getPhotoUrl();
        this.startingPrice = sellableItem.getStartingPrice();
        this.purchasePrice = sellableItem.getPurchasePrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
