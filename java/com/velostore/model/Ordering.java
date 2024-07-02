package com.velostore.model;

import com.velostore.controller.main.Main;
import com.velostore.model.enums.OrderingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Ordering {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;
    private double sum;
    @Enumerated(EnumType.STRING)
    private OrderingStatus orderingStatus;

    public Ordering() {
        this.quantity = 0;
        this.sum = 0;
        orderingStatus = OrderingStatus.ISSUED;
    }

    public void addProductAndUser(Product product, User user) {
        product.addOrdering(this);
        user.addOrdering(this);
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
        this.sum += Main.round(quantity * product.getPrice(), 2);
        this.product.removeQuantity(quantity);
    }

    public void removeQuantity() {
        this.product.addQuantity(this.quantity);
    }


}
