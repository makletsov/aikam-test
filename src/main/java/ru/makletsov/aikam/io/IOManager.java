package ru.makletsov.aikam.io;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface IOManager {
    BufferedReader getReader();

    PrintWriter getWriter();
}
