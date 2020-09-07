package ru.makletsov.aikam.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public interface OutputManager {
    PrintWriter getWriter() throws FileNotFoundException;
}
