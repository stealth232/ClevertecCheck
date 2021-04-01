package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.check.exception.ServiceException;
import ru.clevertec.check.service.OrderCreatorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static ru.clevertec.check.service.CheckConstants.*;

@AllArgsConstructor
@Service
public class OrderCreatorServiceImpl implements OrderCreatorService {

    @Override
    public Map<String, Integer> makeOrder(List<String> list) throws ServiceException {
        BiFunction<Integer, Integer, Integer> bFunc = (oldValue, newValue) -> oldValue + newValue;
        Map<String, Integer> map = new HashMap<>();
        String[] temp;
        String key;
        int value;
        try {
            for (int i = ZERO_INT; i < list.size(); i++) {
                temp = list.get(i).strip().split(QUANTITY_DELIMITER);
                try {
                    key = temp[ZERO_INT];
                    if (list.size() == ZERO_INT) {
                        throw new ServiceException(PLEASE);
                    } else {
                        value = Integer.parseInt(temp[ONE_INT]);
                        map.merge(key, value, bFunc);
                    }
                } catch (NumberFormatException e) {
                    throw new ServiceException(INCORRECT_PARAMS);
                }
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(INCORRECT_PARAMS);
        }
        return map;
    }
}
