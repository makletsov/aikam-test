package ru.makletsov.aikam.result.search.criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Session;
import ru.makletsov.aikam.entities.Customer;

import java.util.List;

public class ProductMinTimesCriterion implements Criterion {
    private final String productName;
    private final Integer minTimes;

    @JsonCreator
    public ProductMinTimesCriterion(@JsonProperty("productName") String productName,
                                    @JsonProperty("minTimes") Integer minTimes) {
        this.productName = productName;
        this.minTimes = minTimes;
    }

    @Override
    @JsonIgnore
    public List<Customer> getCustomersList(Session session) {
        String queryString = String.format("SELECT c " +
                "FROM Purchase AS p " +
                "INNER JOIN p.customer AS c " +
                "INNER JOIN p.product AS pr " +
                "WHERE pr.productName = '%s' " +
                "GROUP BY c " +
                "HAVING COUNT(*) > %d", productName, minTimes);

        return Criterion.getQuery(session, queryString).list();
    }
}
