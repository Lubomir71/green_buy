package com.gfa.green_buy.model.dto;

public class BidDTO {
    private String username;
    private Integer dollars;

    public BidDTO() {
    }

    public BidDTO(String username, Integer dollars) {
        this.username = username;
        this.dollars = dollars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDollars() {
        return dollars;
    }

    public void setDollars(Integer dollars) {
        this.dollars = dollars;
    }
}
