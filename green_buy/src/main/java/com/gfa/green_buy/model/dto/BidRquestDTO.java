package com.gfa.green_buy.model.dto;

public class BidRquestDTO {

    private Long id;
    private Integer dollars;

    public BidRquestDTO() {
    }

    public BidRquestDTO(Long id, Integer dollars) {
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
