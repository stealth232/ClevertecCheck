package ru.clevertec.check.service;

import ru.clevertec.check.exception.ServiceException;
import ru.clevertec.check.observer.entity.State;

public interface MailService {

    void sendMail() throws ServiceException;

    void sendMailToListener(String recipient, State eventType, String messageText, String filePath) throws ServiceException;

    boolean sendMailToUser(String recipient) throws ServiceException;
}
