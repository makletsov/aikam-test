package ru.makletsov.aikam.result.search.criteria;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class SearchCriteria {
    private List<JsonNode> criteria;

    public List<JsonNode> getList() {
        return criteria;
    }

    @JsonSetter("criterias")
    public void setCriteria(List<JsonNode> criterias) {
        this.criteria = criterias;
    }
}
