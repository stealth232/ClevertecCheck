package ru.clevertec.check.utils.creator.impl;

import ru.clevertec.check.annotations.log.LogLevel;
import ru.clevertec.check.annotations.log.LogMe;
import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.utils.creator.OrderCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class OrderCreatorImpl implements OrderCreator {
    private Repository repository = Repository.getInstance();

    private final String QUANTITY_DELIMITER = "-";
    private final String CARD = "card";
    private final String INCORRECT_PARAMS = "Parameters are incorrect. Please try again";
    private final String PLEASE = "Please try to buy something at our store";

    @LogMe(LogLevel.ERROR)
    @Override
    public Map<String, Integer> makeOrder(List<String> list) throws ProductException {
        BiFunction<Integer, Integer, Integer> bFunc = (oldValue, newValue) -> oldValue + newValue;
        Map<String, Integer> map = new HashMap<>();
        String[] temp;
        String key;
        int value;
        int repositorySize = repository.getSize() + 1;
        try {
            for (int i = 0; i <= list.size() - 1; i++) {
                temp = list.get(i).strip().split(QUANTITY_DELIMITER);
                try {
                    key = temp[0];
                    if (list.size() == 0) {
                        throw new ProductException(PLEASE);
                    }
                    if (!temp[0].equals(CARD) && repositorySize < Integer.parseInt(temp[0])) {
                        throw new ProductException("There are only " + repositorySize + " products in our test store. " +
                                "Please try again");
                    } else {
                        value = Integer.parseInt(temp[1]);
                        map.merge(key, value, bFunc);
                    }
                } catch (NumberFormatException e) {
                    throw new ProductException(INCORRECT_PARAMS);
                }
            }
        } catch (NumberFormatException e) {
            throw new ProductException(INCORRECT_PARAMS);
        }
        return map;
    }
}
