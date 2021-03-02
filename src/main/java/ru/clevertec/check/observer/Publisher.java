package ru.clevertec.check.observer;

import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.entity.State;
import ru.clevertec.check.observer.listeners.EventListener;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publisher {
    Map<State, List<EventListener>> listeners = new HashMap<>();

    public Publisher(State... states) {
        for (State state : states) {
            this.listeners.put(state, new ArrayList<>());
        }
    }

    public void subscribe(State eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        eventListeners.add(listener);
    }

    public void unsubscribe(State eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        eventListeners.remove(listener);
    }

    public void notify(State eventType, String message) throws IOException, MessagingException, ProductException {
        List<EventListener> eventListeners = listeners.get(eventType);
        for (EventListener eventListener : eventListeners) {
            eventListener.update(eventType, message);
        }
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "listeners=" + listeners +
                '}';
    }
}
