package ru.makletsov.aikam.result.search.criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Session;
import ru.makletsov.aikam.entities.Customer;

import java.util.List;

public class BadCustomersCriterion implements Criterion {
    private final int badCustomers;

    @JsonCreator
    public BadCustomersCriterion(@JsonProperty("badCustomers") int badCustomers) {
        this.badCustomers = badCustomers;
    }

    @Override
    @JsonIgnore
    public List<Customer> getCustomersList(Session session) {
        String queryString = String.format("SELECT c " +
                "FROM Purchase AS p " +
                "INNER JOIN p.customer AS c " +
                "INNER JOIN p.product AS pr " +
                "GROUP BY c " +
                "ORDER BY SUM(pr.price)");

        return Criterion.getQuery(session, queryString).setMaxResults(badCustomers).list();
    }
}
