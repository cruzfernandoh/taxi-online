package org.taxionline.infra.javalin;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HandlerType;
import io.javalin.router.Endpoint;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.taxionline.adapter.inboud.account.AccountResourceAdapter;
import org.taxionline.adapter.inboud.ride.RideResourceAdapter;
import org.taxionline.infra.di.BeanRegistry;
import org.taxionline.infra.exception.DuplicateAttributeException;
import org.taxionline.infra.exception.ResourceNotFoundException;
import org.taxionline.infra.handler.ExceptionResponseBuilder;

public class JavalinConfig {

    public static void registerExceptions(Javalin app) {
        app.exception(ResourceNotFoundException.class, registerException(404))
                .exception(DuplicateAttributeException.class, registerException(409))
                .exception(ValidationException.class, registerException(400))
                .exception(ConstraintViolationException.class, registerException(400))
                .exception(Exception.class, registerException(500));
    }

    public static void registerEndpoints(Javalin app, BeanRegistry registry) {
        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/account/{identifier}", registry.getBean(AccountResourceAdapter.class)::getAccountByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/account", registry.getBean(AccountResourceAdapter.class)::createAccount));

        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/ride/{identifier}", registry.getBean(RideResourceAdapter.class)::getRideByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/ride", registry.getBean(RideResourceAdapter.class)::requestRide));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/ride/accept-ride/{ride-identifier}/{driver-identifier}", registry.getBean(RideResourceAdapter.class)::acceptRide));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/ride/start-ride/{identifier}", registry.getBean(RideResourceAdapter.class)::startRide));
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
