package ru.makletsov.aikam;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import ru.makletsov.aikam.io.FileInputManager;
import ru.makletsov.aikam.io.FileOutputManager;
import ru.makletsov.aikam.io.InputManager;
import ru.makletsov.aikam.io.OutputManager;
import ru.makletsov.aikam.result.Result;
import ru.makletsov.aikam.result.error.Error;
import ru.makletsov.aikam.result.search.*;
import ru.makletsov.aikam.result.search.criteria.SearchCriteria;
import ru.makletsov.aikam.result.statistic.DateRange;
import ru.makletsov.aikam.result.statistic.StatisticManager;

import java.io.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = getObjectMapper();
        ObjectWriter objectWriter = getObjectWriter(objectMapper);

        InputParametersHandler argsHandler = new InputParametersHandler(args);

        OutputManager outputManager = new FileOutputManager(argsHandler.getDestination());
        PrintWriter writer;

        try {
            writer = outputManager.getWriter();
        } catch (FileNotFoundException e) {
            printError(e.getMessage());
            return;
        }

        try {
            InputManager inputManager = new FileInputManager(argsHandler.getSource());
            BufferedReader reader = inputManager.getReader();

            Session session = getSession();

            Result result = getResult(argsHandler.getRunMode(), objectMapper, reader, session);

            writer.print(objectWriter.writeValueAsString(result));
            writer.flush();
        } catch (Exception e) {
            Result result = new Error(e.getMessage());

            try {
                writer.print(objectWriter.writeValueAsString(result));
                writer.flush();
            } catch (JsonProcessingException eInner) {
                printError(e.getMessage());
                printError(eInner.getMessage());
            }
        } finally {
            if (writer.checkError()) {
                printError("Запись в файл завершилась с ошибкой.");
            }
        }
    }

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    }

    private static ObjectWriter getObjectWriter(ObjectMapper objectMapper) {
        return objectMapper
                .writer(new DefaultPrettyPrinter()
                        .withArrayIndenter(new DefaultIndenter()))
                .with(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    }

    private static void printError(String message) {
        System.err.println("[ERROR] : " + message);
    }

    private static Result getResult(String runMode, ObjectMapper objectMapper, Reader reader, Session session) throws IOException {
        Result result;

        try {
            switch (runMode) {
                case "search":
                    SearchCriteria criteria = objectMapper
                            .treeToValue(objectMapper
                                    .readTree(reader), SearchCriteria.class);

                    if (criteria.getList() == null) {
                        throw new IllegalStateException("Ошибка чтения исходных данный операции " + runMode + ". " +
                                "Проверьте входные данные");
                    }

                    result = new SearchResult(criteria.getList()
                            .stream()
                            .map(jsonNode -> new SingleSearch(jsonNode, session))
                            .collect(Collectors
                                    .toList()));
                    break;
                case "stat":
                    DateRange inputRange = objectMapper
                            .treeToValue(objectMapper
                                    .readTree(reader), DateRange.class);

                    if (inputRange.getStartDate() == null
                            || inputRange.getEndDate() == null) {
                        throw new IllegalStateException("Ошибка чтения исходных данный операции " + runMode + ". " +
                                "Проверьте входные данные");
                    }

                    result = StatisticManager.getStatistic(
                            inputRange.getStartDate(),
                            inputRange.getEndDate(),
                            session);
                    break;
                default:
                    throw new IllegalArgumentException("Недопустимый тип операции: " + runMode + ".");
            }
        } catch (JsonParseException e) {
            throw new IOException ("Ошибка форматирования json при чтении входных данных");
        }

        return result;
    }

    private static Session getSession() throws Exception {
        Configuration configuration = new Configuration();

        try {
            configuration.configure();
        } catch (Exception e) {
            throw new Exception("Ошибка конфигурации доступа к базе данных");
        }

        Session session;

        try {
            session = configuration.buildSessionFactory().openSession();
        } catch (HibernateException e) {
            throw new Exception("Ошибка во время доступа к базе данных");
        }

        return session;
    }
}
