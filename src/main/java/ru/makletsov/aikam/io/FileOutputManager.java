package ru.makletsov.aikam.io;

import java.io.*;

public class FileOutputManager implements OutputManager {
    private final File outputFile;

    public FileOutputManager(String outputFileName) {
        if (outputFileName == null) {
            throw new NullPointerException("Параметр имени файла результата = null.");
        }

        outputFile = new File(outputFileName);
    }

    @Override
    public PrintWriter getWriter() throws FileNotFoundException {
        PrintWriter writer;

        try {
            writer = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Заданный выходной файл существует, но является дирректорией, "
                    + System.lineSeparator() +
                    "не существует и не может быть создан по неизвестной причине или"
                    + System.lineSeparator() +
                    "существует но не может быть открыт по неизвестной причине " + outputFile.getName() + ".");
        } catch (SecurityException e) {
            throw new SecurityException("Запись в файл невозможна по соображениям безопасности "
                    + outputFile.getName() + ".");
        }

        return writer;
    }
}
