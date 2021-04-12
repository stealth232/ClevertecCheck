package ru.clevertec.check.service;

import ru.clevertec.check.exception.ServiceException;

public interface PrintService {
    void printCheck(StringBuilder sb) throws ServiceException;

    void printPDFCheck(StringBuilder sb) throws ServiceException;
}
