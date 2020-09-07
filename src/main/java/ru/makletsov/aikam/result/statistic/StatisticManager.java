package ru.makletsov.aikam.result.statistic;

import org.hibernate.Session;
import ru.makletsov.aikam.entities.Customer;
import ru.makletsov.aikam.entities.Product;
import ru.makletsov.aikam.entities.Purchase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticManager {
    public static StatisticResult getStatistic(LocalDate start, LocalDate end, Session session) {
        if (start == null) {
            throw new NullPointerException("Given start date is null");
        }

        if (end == null) {
            throw new NullPointerException("Given end date is null");
        }

        if (session == null) {
            throw new NullPointerException("Given session is null");
        }

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Given start date is after the given end date");
        }

        @SuppressWarnings("unchecked")
        List<Customer> customers = session.createQuery("FROM Customer").list();

        List<LocalDate> weekends = getWeekends(start, end);
        List<CustomerStatistic> customerStatistics = new ArrayList<>();

        for (Customer customer : customers) {
            Map<Product, List<Purchase>> purchasesByProducts = customer.getPurchases()
                    .stream()
                    .filter(purchase ->
                            isInRange(purchase.getDate(), start, end)
                                    && isNotWeekend(purchase.getDate()))
                    .collect(Collectors.groupingBy(Purchase::getProduct));

            List<ProductStatistic> productStatistics = purchasesByProducts.entrySet().stream()
                    .map(e -> new ProductStatistic(e.getKey(), e.getValue())).collect(Collectors.toList());

            CustomerStatistic customerStatistic = new CustomerStatistic(customer, productStatistics);
            customerStatistics.add(customerStatistic);
        }

        long totalDays = getTotalDays(start, end, weekends.size());

        return new StatisticResult(totalDays, customerStatistics);
    }

    private static List<LocalDate> getWeekends(LocalDate start, LocalDate end) {
        LocalDate nextSaturday = start
                .plusDays(7 - start
                        .getDayOfWeek()
                        .getValue())
                .minusDays(1);

        List<LocalDate> weekends = new ArrayList<>();

        while (!nextSaturday.isAfter(end)) {
            weekends.add(nextSaturday);

            if (!nextSaturday.plusDays(1).isAfter(end)) {
                weekends.add(nextSaturday.plusDays(1));
            }

            nextSaturday = nextSaturday.plusDays(7);
        }

        return weekends;
    }

    private static long getTotalDays(LocalDate start, LocalDate end, int weekendsCount) {
        return start.until(end, ChronoUnit.DAYS) - weekendsCount + 1;
    }

    private static boolean isInRange(LocalDate date, LocalDate start, LocalDate end) {
        return (date.isAfter(start) || date.isEqual(start)) && (date.isBefore(end) || date.isEqual(end));
    }

    private static boolean isNotWeekend(LocalDate date) {
        return !date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
