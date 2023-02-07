package com.gfa.green_buy.model.dto;

public class SellableItemListDTO {
    private String name;
    private String photoUrl;
    private Integer lastOfferedBid;

    public SellableItemListDTO() {
    }

    public SellableItemListDTO(String name, String photoUrl, Integer lastOfferedBid) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.lastOfferedBid = lastOfferedBid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getLastOfferedBid() {
        return lastOfferedBid;
    }

    public void setLastOfferedBid(Integer lastOfferedBid) {
        this.lastOfferedBid = lastOfferedBid;
    }
}
