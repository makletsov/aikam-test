package ru.makletsov.aikam.result.search.criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.Session;
import ru.makletsov.aikam.entities.Customer;

import java.util.List;

public class LastNameCriterion implements Criterion {
    private final String lastName;

    @JsonCreator
    public LastNameCriterion(@JsonProperty("lastName") String lastName) {
        this.lastName = lastName;
    }

    @Override
    @JsonIgnore
    public List<Customer> getCustomersList(Session session) {
        String queryString = String.format("FROM Customer WHERE lastName = '%s'", lastName);

        return Criterion.getQuery(session, queryString).list();
    }
}
