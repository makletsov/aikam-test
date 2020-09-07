package ru.makletsov.aikam.result.search.criteria;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.Session;
import ru.makletsov.aikam.entities.Customer;

import java.math.BigDecimal;
import java.util.List;

public class ExpensesCriterion implements Criterion {
    private final BigDecimal minExpenses;
    private final BigDecimal maxExpenses;

    @JsonCreator
    public ExpensesCriterion(@JsonProperty("minExpenses") BigDecimal minExpenses,
                             @JsonProperty("maxExpenses") BigDecimal maxExpenses) {
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
    }

    @Override
    @JsonIgnore
    public List<Customer> getCustomersList(Session session) {
        String queryString = String.format("SELECT c " +
                        "FROM Purchase AS p " +
                        "INNER JOIN p.customer AS c " +
                        "INNER JOIN p.product AS pr " +
                        "GROUP BY c " +
                        "HAVING SUM(pr.price) BETWEEN %s AND %s",
                minExpenses.doubleValue(),
                maxExpenses.doubleValue());

        return Criterion.getQuery(session, queryString).list();
    }
}
