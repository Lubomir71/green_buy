package com.gfa.green_buy.model.dto;

public class BidDTO {

    private Long id;
    private Integer dollars;

    public BidDTO() {
    }

    public BidDTO(Long id, Integer dollars) {
        this.id = id;
        this.dollars = dollars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDollars() {
        return dollars;
    }

    public void setDollars(Integer dollars) {
        this.dollars = dollars;
    }
}
