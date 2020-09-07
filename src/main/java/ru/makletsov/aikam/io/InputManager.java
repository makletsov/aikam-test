package ru.makletsov.aikam.io;

import java.io.BufferedReader;
import java.io.IOException;

public interface InputManager {
    BufferedReader getReader() throws IOException;
}
