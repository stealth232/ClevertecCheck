package ru.clevertec.check.utils.parser.impl;

import ru.clevertec.check.annotations.log.LogLevel;
import ru.clevertec.check.annotations.log.LogMe;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.utils.parser.ArgsParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArgsParserImpl implements ArgsParser {

    private static final String DELIMITER = "\\s";
    private static final String TXT = "txt";
    private static final String XML = "xml";
    private static final String BIN = "bin";

    @LogMe(LogLevel.FATAL)
    @Override
    public List<String> parsParams(String[] args) throws ProductException {
        List<String> products = null;
        if (args[0].contains(TXT) || args[0].contains(XML) || args[0].contains(BIN)) {
            String filePath = args[0];
            products = parsParams(filePath, products);
        } else {
            products = Arrays.stream(args).collect(Collectors.toList());
        }
        return products;
    }

    public List<String> parsParams(String filePath, List<String> products) throws ProductException {
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            products = Arrays.asList(bufferedReader.readLine().split(DELIMITER));
        } catch (FileNotFoundException e) {
            throw new ProductException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new ProductException("File reading error: " + filePath, e);
        }
        return products;
    }

}
