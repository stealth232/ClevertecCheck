package ru.clevertec.check.observer.listeners;

import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.entity.State;

public interface EventListener {
    void update(State eventType, String message) throws ProductException;
}
