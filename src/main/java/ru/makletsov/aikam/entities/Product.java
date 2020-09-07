package ru.makletsov.aikam.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products", schema = "public", catalog = "shop")
public class Product {
    private int id;
    private String productName;
    private BigDecimal price;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
