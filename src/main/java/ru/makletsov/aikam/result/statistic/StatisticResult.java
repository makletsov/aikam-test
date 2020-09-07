package ru.makletsov.aikam.result.statistic;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.makletsov.aikam.result.Result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@JsonPropertyOrder({"type", "totalDays", "customers", "totalExpenses", "avgExpenses"})
public class StatisticResult implements Result {
    private final String type;
    private final long totalDays;
    private final Collection<CustomerStatistic> customerStatistics;
    private final BigDecimal totalExpanses;
    private final BigDecimal averageExpanses;

    private final static String TYPE = "stat";

    public StatisticResult(long totalDays, Collection<CustomerStatistic> customerStatistics) {
        type = TYPE;
        this.totalDays = totalDays;
        this.customerStatistics = customerStatistics;

        totalExpanses = customerStatistics
                .stream()
                .map(CustomerStatistic::getTotalExpenses)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        averageExpanses =
                totalExpanses.divide(BigDecimal.valueOf(customerStatistics.size()), RoundingMode.HALF_UP);
    }

    @Override
    public String getType() {
        return type;
    }

    public long getTotalDays() {
        return totalDays;
    }

    @JsonGetter("customers")
    public Collection<CustomerStatistic> getCustomerStatistics() {
        return customerStatistics;
    }

    public BigDecimal getTotalExpanses() {
        return totalExpanses;
    }

    @JsonGetter("avgExpenses")
    public BigDecimal getAverageExpanses() {
        return averageExpanses;
    }

    public StatisticResult(long totalDays, Collection<CustomerStatistic> customerStatistics,
                           BigDecimal totalExpanses, BigDecimal averageExpanses) {
        type = TYPE;

        this.totalDays = totalDays;
        this.customerStatistics = customerStatistics;
        this.totalExpanses = totalExpanses;
        this.averageExpanses = averageExpanses;
    }
}
