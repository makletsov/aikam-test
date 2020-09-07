package ru.makletsov.aikam.result.error;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.makletsov.aikam.result.Result;

@JsonPropertyOrder({"type", "message"})
public class Error implements Result {
    private final String type;
    private final String message;

    private final static String TYPE = "error";

    public Error(String message) {
        type = TYPE;

        this.message = message;
    }

    @Override
    @JsonGetter
    public String getType() {
        return type;
    }

    @JsonGetter
    public String getMessage() {
        return message;
    }
}
