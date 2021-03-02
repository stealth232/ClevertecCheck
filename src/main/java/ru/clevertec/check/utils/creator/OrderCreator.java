package ru.clevertec.check.utils.creator;

import ru.clevertec.check.exception.ProductException;

import java.util.List;
import java.util.Map;

public interface OrderCreator {
    Map<String, Integer> makeOrder(List<String> list) throws ProductException;
}
