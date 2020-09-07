package ru.makletsov.aikam.io;

import java.io.*;

public class FileInputManager implements InputManager {
    private final File inputFile;

    public FileInputManager(String inputFileName) throws IOException {
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

        inputFile = file;
    }

    @Override
    public BufferedReader getReader() throws FileNotFoundException {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Не удается открыть файл для чтения по неизвестной причине " + inputFile.getName() + ".");
        }

        return reader;
    }
}
