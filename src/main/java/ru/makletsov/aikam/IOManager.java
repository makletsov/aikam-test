package ru.makletsov.aikam;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface IOManager {
    BufferedReader getReader();

    PrintWriter getWriter();
}
