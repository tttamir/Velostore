package com.velostore.model;

import com.velostore.controller.main.Main;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class StatProduct {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne
    private Product product;

    private int quantity;
    private double income;

    public StatProduct() {
        this.quantity = 0;
        this.income = 0;
    }

    public void addIncome(int quantity, double sum) {
        this.quantity += quantity;
        this.income += Main.round(sum, 2);
    }
}
