package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.check.annotations.log.LogLevel;
import ru.clevertec.check.annotations.log.LogMe;
import ru.clevertec.check.exception.ServiceException;
import ru.clevertec.check.service.ArgsParserService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.clevertec.check.service.CheckConstants.*;

@AllArgsConstructor
@Service
public class ArgsParserServiceImpl implements ArgsParserService {

    @Override
    public List<String> parsParams(String[] args) throws ServiceException {
        List<String> products;
        if (args[ZERO_INT].contains(TXT) || args[0].contains(XML) || args[0].contains(BIN)) {
            String filePath = args[ZERO_INT];
            products = parsParams(filePath);
        } else {
            products = Arrays.stream(args).collect(Collectors.toList());
        }
        return products;
    }

    @Override
    public List<String> parsParams(String filePath) throws ServiceException {
         List<String> products;
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            products = Arrays.asList(bufferedReader.readLine().split(DELIMITER));
        } catch (FileNotFoundException e) {
            throw new ServiceException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new ServiceException("File reading error: " + filePath, e);
        }
        return products;
    }
}
