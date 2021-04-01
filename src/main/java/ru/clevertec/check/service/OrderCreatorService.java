package ru.clevertec.check.service;

import ru.clevertec.check.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface OrderCreatorService {

    Map<String, Integer> makeOrder(List<String> list) throws ServiceException;
}
