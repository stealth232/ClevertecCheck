package ru.clevertec.check.service;

import ru.clevertec.check.entities.product.Order;
import ru.clevertec.check.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CheckService {

    StringBuilder showCheck(Map<String, Integer> map);

    StringBuilder getHTML(Map<String, Integer> map);

    StringBuilder getPDF(Map<String, Integer> map);

    Order getOrder(Map<String, Integer> map);

    void printCheck(StringBuilder sb) throws ServiceException;

    void printPDFCheck(StringBuilder sb) throws ServiceException;

    Map<String, Integer> selectProducts(HttpServletRequest request);
}
