package ru.makletsov.aikam.result.statistic;

import ru.makletsov.aikam.entities.Product;
import ru.makletsov.aikam.entities.Purchase;

import java.math.BigDecimal;
import java.util.Collection;

public class ProductStatistic {
    private final String productName;
    private final BigDecimal expenses;

    public ProductStatistic(String productName, BigDecimal expenses) {
        this.productName = productName;
        this.expenses = expenses;
    }

    public ProductStatistic(Product product, Collection<Purchase> purchases) {
        productName = product.getProductName();

        expenses = purchases.stream()
                .map(p -> p.getProduct().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }
}
