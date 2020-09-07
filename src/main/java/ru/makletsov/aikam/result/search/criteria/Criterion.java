package ru.makletsov.aikam.result.search.criteria;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.makletsov.aikam.entities.Customer;

import java.util.List;

public interface Criterion {
    List<Customer> getCustomersList(Session session);

    static Query<Customer> getQuery(Session session, String queryString) {
        @SuppressWarnings("unchecked")
        Query<Customer> query = session.createQuery(queryString);

        return query;
    }
}
