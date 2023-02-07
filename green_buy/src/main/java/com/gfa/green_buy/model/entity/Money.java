package com.gfa.green_buy.model.entity;

import jakarta.persistence.*;

@Entity
public class Money {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    Integer dollars;

    public Money() {
    }

    public Money(Integer dollars,User user) {
        this.dollars = dollars;
        this.user = user;
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

    public Integer getDollars() {
        return dollars;
    }

    public void setDollars(Integer dollars) {
        this.dollars = dollars;
    }
}
