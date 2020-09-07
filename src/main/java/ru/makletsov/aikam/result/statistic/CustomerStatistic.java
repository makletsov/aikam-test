package ru.makletsov.aikam.result.statistic;

import com.fasterxml.jackson.annotation.JsonGetter;
import ru.makletsov.aikam.entities.Customer;

import java.math.BigDecimal;
import java.util.List;

public class CustomerStatistic {
    private final String name;
    private final List<ProductStatistic> productStatistics;
    private BigDecimal totalExpenses;

    public CustomerStatistic(Customer customer, List<ProductStatistic> productStatistics) {
        if (customer == null) {
            throw new NullPointerException("Given customer is null");
        }

        name = customer.getFirstName() + " " + customer.getLastName();
        this.productStatistics = productStatistics;

        totalExpenses = productStatistics
                .stream()
                .map(ProductStatistic::getExpenses)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getName() {
        return name;
    }

    @JsonGetter("purchases")
    public List<ProductStatistic> getProductStatistics() {
        return productStatistics;
    }

    @JsonGetter("totalExpenses")
    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }
}
