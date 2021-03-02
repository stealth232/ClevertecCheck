package ru.clevertec.check.observer.listeners;

import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.entity.State;
import ru.clevertec.check.utils.mailer.JavaMailUtil;

import static ru.clevertec.check.service.CheckConstants.CHECKFILETXT;
import static ru.clevertec.check.utils.mailer.JavaMailProperties.MAIL_STEALTH;

public class Consoler implements EventListener {

    @Override
    public void update(State eventType, String message) throws ProductException {
        JavaMailUtil.sendMailToListener(MAIL_STEALTH, eventType,
                message, CHECKFILETXT);
    }
}
