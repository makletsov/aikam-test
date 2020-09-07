package ru.makletsov.aikam.result.search;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.Session;
import ru.makletsov.aikam.entities.Customer;
import ru.makletsov.aikam.result.search.criteria.CriteriaFactory;

import java.util.List;

@JsonPropertyOrder({"criteria", "results"})
public class SingleSearch {
    private final JsonNode jsonNode;
    private final List<Customer> results;

    public SingleSearch(JsonNode jsonNode, Session session) {
        if (jsonNode == null) {
            throw new NullPointerException("Given json node is null");
        }

        if (session == null) {
            throw new NullPointerException("Given session is null");
        }

        if (!session.isOpen()) {
            throw new IllegalStateException("Given session is closed");
        }

        this.jsonNode = jsonNode;
        results = CriteriaFactory
                .getCriterion(jsonNode)
                .getCustomersList(session);
    }

    @JsonGetter("criteria")
    public JsonNode getJsonNode() {
        return jsonNode;
    }

    public List<Customer> getResults() {
        return results;
    }
}
