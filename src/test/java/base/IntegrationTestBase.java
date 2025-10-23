package base;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.router.Endpoint;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.adapter.inboud.AccountResource;
import org.taxionline.adapter.outbound.AccountRepositoryAdapter;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.config.handler.RegisterJavalinException;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.di.BeanRegistry;
import org.taxionline.port.account.AccountRepository;

public abstract class IntegrationTestBase {

    private final static Logger logger = LoggerFactory.getLogger(IntegrationTestBase.class);

    protected static Javalin app;

    @BeforeAll
    static void startServer() {
        logger.info("Initialize testing environment...");

        DataSourceManager manager = new DataSourceManagerTest();
        manager.init();

        BeanRegistry registry = new BeanRegistry();
        registry.registerBean(DataSourceManager.class, manager);
        registry.registerBean(AccountRepository.class, new AccountRepositoryAdapter());
        registry.registerBean(AccountBusiness.class, new AccountBusiness());
        registry.registerBean(AccountResource.class, new AccountResource());
        registry.injectDependencies();

        logger.info("In-memory H2 database started for testing.");

        app = Javalin
                .create(t -> {
                    t.router.contextPath = "/api";
                    t.http.defaultContentType = "application/json";
                });
        RegisterJavalinException.register(app);
        app.addEndpoint(new Endpoint(HandlerType.valueOf("GET"), "/account/{identifier}", registry.getBean(AccountResource.class)::getAccountByIdentifier));
        app.addEndpoint(new Endpoint(HandlerType.valueOf("POST"), "/account", registry.getBean(AccountResource.class)::createAccount));
        app.start("localhost", 8081);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        logger.info("Server started at http://localhost:8081");
    }

    @AfterAll
    static void stopServer() {
        if (app != null) {
            app.stop();
            logger.info("Test server finalized.");
        }
    }
}
