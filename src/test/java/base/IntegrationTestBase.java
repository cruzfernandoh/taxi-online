package base;

import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.config.di.BeanRegistry;
import org.taxionline.config.javalin.JavalinConfig;

public abstract class IntegrationTestBase {

    private final static Logger logger = LoggerFactory.getLogger(IntegrationTestBase.class);

    protected static Javalin app;

    protected static BeanRegistry registry;

    protected static DataSourceManager manager;

    @BeforeAll
    static void startServer() {
        if (app != null) {
            logger.info("Server already running, skipping startup.");
            return;
        }

        logger.info("Initialize testing environment...");

        manager = new DataSourceManagerTest();
        manager.init();

        registry = new BeanRegistry();
        registry.registerAllBeans(manager);
        registry.injectDependencies();

        logger.info("In-memory H2 database started for testing.");

        app = JavalinConfig.createJavalin();
        JavalinConfig.registerExceptions(app);
        JavalinConfig.registerEndpoints(app, registry);
        app.start("localhost", 8081);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        logger.info("Server started at http://localhost:8081");
    }
}
