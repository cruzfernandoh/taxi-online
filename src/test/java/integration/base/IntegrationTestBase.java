package integration.base;

import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.TaxiOnlineMain;
import org.taxionline.infra.dao.DataSourceManager;
import org.taxionline.infra.di.BeanRegistry;
import org.taxionline.infra.javalin.JavalinConfig;
import org.taxionline.infra.mediator.Mediator;
import org.taxionline.util.AppConfigUtils;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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

        var yaml = new Yaml(new Constructor(AppConfigUtils.class, new LoaderOptions()));
        var config = (AppConfigUtils) yaml.load(TaxiOnlineMain.class.getClassLoader().getResourceAsStream("config-test.yaml"));

        manager = new DataSourceManagerTest();
        manager.init(config);

        registry = new BeanRegistry();

        var mediator = new Mediator();
//        mediator.register(RideCompletedEvent.eventName, (data) -> new ProcessPaymentBusiness().processPayment((RideCompletedEvent) data));

        registry.registerAllBeans(manager, mediator);
        registry.injectDependencies();

        logger.info("In-memory H2 database started for testing.");

        app = JavalinConfig.createJavalin();
        JavalinConfig.registerExceptions(app);
        JavalinConfig.registerEndpoints(app, registry);
        app.start("localhost", config.getServer().getPort());
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = config.getServer().getPort();
        logger.info("Server started at http://localhost:{}", config.getServer().getPort());
    }
}
