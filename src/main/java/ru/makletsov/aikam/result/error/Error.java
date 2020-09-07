package ru.makletsov.aikam.result.error;

import ru.makletsov.aikam.result.Result;

public class Error implements Result {
    private final String type;
    private final String message;

    private final String TYPE = "error";

    public Error(String message) {
        type = TYPE;

        this.message = message;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
