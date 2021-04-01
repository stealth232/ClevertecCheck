package ru.clevertec.check.service;

import ru.clevertec.check.exception.ServiceException;

import java.util.List;

public interface ArgsParserService {

    List<String> parsParams(String[] args) throws ServiceException;

    List<String> parsParams(String filePath) throws ServiceException;

}
