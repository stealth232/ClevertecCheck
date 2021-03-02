package ru.clevertec.check.observer.listeners;

import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.entity.State;
import ru.clevertec.check.utils.mailer.JavaMailUtil;

import static ru.clevertec.check.service.CheckConstants.CHECKFILEPDF;
import static ru.clevertec.check.utils.mailer.JavaMailProperties.MAIL_STEALTH;

public class Emailer implements EventListener {

    @Override
    public void update(State eventType, String message) throws ProductException {
        JavaMailUtil.sendMailToListener(MAIL_STEALTH, eventType,
                message, CHECKFILEPDF);
    }
}
