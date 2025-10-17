package org.taxionline;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HandlerType;
import io.javalin.router.Endpoint;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.adapter.inboud.AccountResource;
import org.taxionline.adapter.outbound.AccountRepositoryAdapter;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.config.exception.DuplicateAttributeException;
import org.taxionline.config.exception.ResourceNotFoundException;
import org.taxionline.config.handler.ExceptionResponseBuilder;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.di.BeanRegistry;
import org.taxionline.port.account.AccountRepository;
import org.taxionline.util.AppConfigUtils;

public class TaxiOnlineMain {
    private static final Logger logger = LoggerFactory.getLogger(TaxiOnlineMain.class);

    public static void main(String[] args) {
        AppConfigUtils config = AppConfigUtils.getInstance();
        logger.info("Application config success loaded: {}", config.get("app.name"));

        BeanRegistry registry = new BeanRegistry();
        registry.registerBean(AccountRepository.class, new AccountRepositoryAdapter());
        registry.registerBean(AccountBusiness.class, new AccountBusiness());
        registry.registerBean(AccountResource.class, new AccountResource());
        registry.injectDependencies();

        Javalin app = Javalin
                .create(t -> {
                    t.router.contextPath = "/api";
                    t.http.defaultContentType = "application/json";
                })
                .exception(ResourceNotFoundException.class, registerException(404))
                .exception(DuplicateAttributeException.class, registerException(409))
                .exception(ValidationException.class, registerException(404))
                .exception(ConstraintViolationException.class, registerException(404))
                .exception(Exception.class, registerException(500))
                .start(Integer.parseInt(config.get("server.port")));

        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/account/{identifier}", registry.getBean(AccountResource.class)::getAccountByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/account", registry.getBean(AccountResource.class)::createAccount));

        logger.info("Server started on http://localhost:{}/api", config.get("server.port"));

        DataSourceManager.getInstance().init();
    }

    @NotNull
    private static ExceptionHandler<Exception> registerException(int status) {
        return (exception, ctx) -> ExceptionResponseBuilder.build(ctx, exception.getMessage(), status);
    }

}
