package org.taxionline;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.router.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.adapter.inboud.AccountResource;
import org.taxionline.adapter.outbound.AccountRepositoryAdapter;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.config.dao.DataSourceManagerAdapter;
import org.taxionline.config.handler.RegisterJavalinException;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.di.BeanRegistry;
import org.taxionline.port.account.AccountRepository;
import org.taxionline.util.AppConfigUtils;

public class TaxiOnlineMain {
    private static final Logger logger = LoggerFactory.getLogger(TaxiOnlineMain.class);

    public static void main(String[] args) {
        AppConfigUtils config = AppConfigUtils.getInstance();
        logger.info("Application config success loaded: {}", config.get("app.name"));

        DataSourceManager adapter = new DataSourceManagerAdapter();
        adapter.init();
        BeanRegistry registry = new BeanRegistry();
        registry.registerBean(DataSourceManager.class, adapter);
        registry.registerBean(AccountRepository.class, new AccountRepositoryAdapter());
        registry.registerBean(AccountBusiness.class, new AccountBusiness());
        registry.registerBean(AccountResource.class, new AccountResource());
        registry.injectDependencies();

        Javalin app = Javalin
                .create(t -> {
                    t.router.contextPath = "/api";
                    t.http.defaultContentType = "application/json";
                });
        RegisterJavalinException.register(app);
        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/account/{identifier}", registry.getBean(AccountResource.class)::getAccountByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/account", registry.getBean(AccountResource.class)::createAccount));
        app.start("0.0.0.0", Integer.parseInt(config.get("server.port")));
        logger.info("Server started on http://localhost:{}/api", config.get("server.port"));
    }
}
