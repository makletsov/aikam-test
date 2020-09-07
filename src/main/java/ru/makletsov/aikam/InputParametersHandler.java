package ru.makletsov.aikam;

public class InputParametersHandler {
    private final String runMode;
    private final String inputFile;
    private final String outputFile;

    private static final int MIN_ARGUMENTS_COUNT = 3;

    public InputParametersHandler(String[] args) {
        if (args == null) {
            throw new NullPointerException("Массив параметров командной строки = null.");
        }

        checkIfLengthIsInsufficient(args.length);

        for (int i = 0; i < args.length; i++) {
            checkIfArgIsNull(args[i], i);
        }

        runMode = args[0];
        inputFile = args[1];
        outputFile = args[2];
    }

    public String getRunMode() {
        return runMode;
    }

    public String getSource() {
        return inputFile;
    }

    public String getDestination() {
        return outputFile;
    }

    private void checkIfArgIsNull(String arg, int index) {
        if (arg == null) {
            throw new NullPointerException("Параметр с индексом " + index + " = null.");
        }
    }

    private void checkIfLengthIsInsufficient(int argsLength) {
        if (argsLength < MIN_ARGUMENTS_COUNT) {
            throw new IllegalArgumentException("Недостаточно параметров для запуска: нужно "
                    + MIN_ARGUMENTS_COUNT + ", задано " + argsLength + ".");
        }
    }
}
