package com.velostore.model;

import com.velostore.model.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int warranty;
    private String country;
    private String firm;
    private String file;
    private int quantity;
    private String date;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ordering> orderingList;

    @OneToOne(cascade = CascadeType.ALL)
    private StatProduct statProduct;

    public Product(String name, double price, Category category, int warranty, String country, String firm, int quantity, String date) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.warranty = warranty;
        this.country = country;
        this.firm = firm;
        this.quantity = quantity;
        this.date = date;
        this.orderingList = new ArrayList<>();
    }

    public void update(String name, double price, Category category, int warranty, String country, String firm, int quantity, String date) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.warranty = warranty;
        this.country = country;
        this.firm = firm;
        this.quantity = quantity;
        this.date = date;
    }

    public void addOrdering(Ordering ordering) {
        orderingList.add(ordering);
        ordering.setProduct(this);
    }

    public void removeOrdering(Ordering ordering) {
        orderingList.remove(ordering);
        ordering.setProduct(null);
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
