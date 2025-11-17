package org.taxionline.config.mediator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Mediator {

    Map<String, Consumer<Object>> handlers = new HashMap<>();

    public void register(String event, Consumer<Object> function) {
        this.handlers.put(event, function);
    }

    public void notify(String event, Object data) {
        Consumer<Object> handler = handlers.get(event);
        if (handler != null) {
            handler.accept(data);
        }
    }
}
