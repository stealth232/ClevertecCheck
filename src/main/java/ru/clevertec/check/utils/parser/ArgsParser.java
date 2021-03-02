package ru.clevertec.check.utils.parser;

import ru.clevertec.check.exception.ProductException;
import java.util.List;

public interface ArgsParser {
    List<String> parsParams(String[] args) throws ProductException;

}
