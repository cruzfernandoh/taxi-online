package org.taxionline.config.handler;

import io.javalin.http.Context;

import java.time.Instant;
import java.util.Map;

public class ExceptionResponseBuilder {

    public static void build(Context ctx, String message, int status) {
        Map<String, Object> body = Map.of(
                "status", status,
                "message", message,
                "timestamp", Instant.now().toString()
        );
        ctx.status(status).json(body);
    }
}
