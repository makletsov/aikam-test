package ru.makletsov.aikam.result.search;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.makletsov.aikam.result.Result;

import java.util.List;

@JsonPropertyOrder({"type", "results"})
public class SearchResult implements Result {
    private final String type;
    private final List<SingleSearch> results;

    private static final String TYPE = "search";

    public SearchResult(List<SingleSearch> results) {
        if (results == null) {
            throw new NullPointerException("Given results list is null");
        }

        type = TYPE;
        this.results = results;
    }

    @Override
    public String getType() {
        return type;
    }

    public List<SingleSearch> getResults() {
        return results;
    }
}
