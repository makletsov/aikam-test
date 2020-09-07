package ru.makletsov.aikam.result.search.criteria;

import com.fasterxml.jackson.databind.JsonNode;

public class CriteriaFactory {
    public static Criterion getCriterion(JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new NullPointerException("Given json node is null");
        }

        if (hasLastName(jsonNode)) {
            return new LastNameCriterion(jsonNode.get("lastName").asText());
        }

        if (hasProductNameAndTimesCount(jsonNode)) {
            return new ProductMinTimesCriterion(jsonNode.get("productName").asText(),
                    jsonNode.get("minTimes").asInt());
        }

        if (hasMinAndMaxExpanses(jsonNode)) {
            return new ExpensesCriterion(jsonNode.get("minExpenses").decimalValue(),
                    jsonNode.get("maxExpenses").decimalValue());
        }

        if (hasBadCustomersCount(jsonNode)) {
            return new BadCustomersCriterion(jsonNode.get("badCustomers").asInt());
        }

        throw new IllegalArgumentException("Given criterion has an illegal format");
    }

    private static boolean hasLastName(JsonNode jsonNode) {
        return jsonNode.hasNonNull("lastName");
    }

    private static boolean hasProductNameAndTimesCount(JsonNode jsonNode) {
        return jsonNode.hasNonNull("productName") && jsonNode.hasNonNull("minTimes");
    }

    private static boolean hasMinAndMaxExpanses(JsonNode jsonNode) {
        return jsonNode.hasNonNull("minExpenses") && jsonNode.hasNonNull("maxExpenses");
    }

    private static boolean hasBadCustomersCount(JsonNode jsonNode) {
        return jsonNode.hasNonNull("badCustomers");
    }
}