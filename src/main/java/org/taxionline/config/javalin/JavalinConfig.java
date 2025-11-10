package org.taxionline.config.javalin;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HandlerType;
import io.javalin.router.Endpoint;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.taxionline.adapter.inboud.AccountResource;
import org.taxionline.adapter.inboud.RideResource;
import org.taxionline.config.di.BeanRegistry;
import org.taxionline.config.exception.DuplicateAttributeException;
import org.taxionline.config.exception.ResourceNotFoundException;
import org.taxionline.config.handler.ExceptionResponseBuilder;

public class JavalinConfig {

    public static void registerExceptions(Javalin app) {
        app.exception(ResourceNotFoundException.class, registerException(404))
                .exception(DuplicateAttributeException.class, registerException(409))
                .exception(ValidationException.class, registerException(400))
                .exception(ConstraintViolationException.class, registerException(400))
                .exception(Exception.class, registerException(500));
    }

    public static void registerEndpoints(Javalin app, BeanRegistry registry) {
        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/account/{identifier}", registry.getBean(AccountResource.class)::getAccountByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/account", registry.getBean(AccountResource.class)::createAccount));

        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/ride/{identifier}", registry.getBean(RideResource.class)::getRideByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/ride", registry.getBean(RideResource.class)::requestRide));
    }

    public static Javalin createJavalin() {
        return Javalin
                .create(t -> {
                    t.router.contextPath = "/api";
                    t.http.defaultContentType = "application/json";
                });
    }

    @NotNull
    private static ExceptionHandler<Exception> registerException(int status) {
        return (exception, ctx) -> ExceptionResponseBuilder.build(ctx, exception.getMessage(), status);
    }
}
