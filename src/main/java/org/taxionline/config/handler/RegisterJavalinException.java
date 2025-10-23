package org.taxionline.config.handler;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.taxionline.config.exception.DuplicateAttributeException;
import org.taxionline.config.exception.ResourceNotFoundException;

public class RegisterJavalinException {

    public static void register(Javalin app) {
        app.exception(ResourceNotFoundException.class, registerException(404))
                .exception(DuplicateAttributeException.class, registerException(409))
                .exception(ValidationException.class, registerException(400))
                .exception(ConstraintViolationException.class, registerException(400))
                .exception(Exception.class, registerException(500));
    }

    @NotNull
    private static ExceptionHandler<Exception> registerException(int status) {
        return (exception, ctx) -> ExceptionResponseBuilder.build(ctx, exception.getMessage(), status);
    }
}
