package ru.makletsov.aikam.io;

import java.io.*;

public class FileIOManager implements IOManager {
    private final BufferedReader reader;
    private final PrintWriter writer;

    public FileIOManager(String inputFileName, String outputFileName) throws IOException {
        if (inputFileName == null) {
            throw new NullPointerException("Параметр имени файла входных данных = null.");
        }

        File file = new File(inputFileName);

        if (!file.exists()) {
            throw new FileNotFoundException("Не удалось найти заданный входной файл: \"" + inputFileName + "\".");
        }

        if (!file.canRead()) {
            throw new IOException("Заданный входной файл не доступен для записи: \"" + inputFileName + "\".");
        }

        if (file.isHidden()) {
            throw new IOException("Заданный входной файл скрыт и поэтому не доступен: \"" + inputFileName + "\".");
        }

        if (!file.isFile()) {
            throw new FileNotFoundException("Заданный входной файл не является нормальным файлом: \"" + inputFileName +
                    "\". " + System.lineSeparator() +
                    "Возможно он является директорией или не удовлетворяет другим системным требованиям.");
        }

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Не удается открыть файл для чтения по неизвестной причине " + inputFileName + ".");
        }

        if (outputFileName == null) {
            throw new NullPointerException("Параметр имени файла результата = null.");
        }

        try {
            writer = new PrintWriter(outputFileName);
        } catch (IOException e) {
            throw new FileNotFoundException("Заданный выходной файл существует, но является дирректорией, "
                    + System.lineSeparator() +
                    "не существует и не может быть создан по неизвестной причине или"
                    + System.lineSeparator() +
                    "существует но не может быть открыт по неизвестной причине " + outputFileName + ".");
        }

    }

    @Override
    public BufferedReader getReader() {
        return reader;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }
}
