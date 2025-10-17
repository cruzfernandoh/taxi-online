//package base;
//
//import io.agroal.api.AgroalDataSource;
//import io.agroal.api.configuration.AgroalDataSourceConfiguration;
//import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
//import io.agroal.api.security.NamePrincipal;
//import io.restassured.RestAssured;
//import io.undertow.Undertow;
//import org.jboss.resteasy.core.ResteasyDeploymentImpl;
//import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
//import org.jboss.resteasy.spi.ResteasyDeployment;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.taxionline.api.RestApplication;
//
//import java.sql.SQLException;
//
//public abstract class IntegrationTestBase {
//
//    private final static Logger logger = LoggerFactory.getLogger(IntegrationTestBase.class);
//
//    protected static UndertowJaxrsServer server;
//
//    protected static AgroalDataSource dataSource;
//
//    @BeforeAll
//    static void startServer() throws SQLException {
//        logger.info("Initialize testing environment...");
//
//        AgroalDataSourceConfiguration configuration = new AgroalDataSourceConfigurationSupplier()
//                .connectionPoolConfiguration(cp -> cp
//                        .maxSize(5)
//                        .minSize(1)
//                        .initialSize(1)
//                        .connectionFactoryConfiguration(cf -> cf
//                                .jdbcUrl("jdbc:h2:mem:default")
//                                .connectionProviderClassName("org.h2.Driver")
//                                .principal(new NamePrincipal("test_user"))
//                        )
//                ).get();
//
//        dataSource = AgroalDataSource.from(configuration);
//
//        logger.info("In-memory H2 database started for testing.");
//
//        ResteasyDeployment deployment = new ResteasyDeploymentImpl();
//        deployment.setApplicationClass(RestApplication.class.getName());
//
//        server = new UndertowJaxrsServer();
//        server.deploy(deployment);
//        server.start(Undertow.builder().addHttpListener(8081, "0.0.0.0"));
//
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 8081;
//
//        logger.info("Server started at http://localhost:8081");
//    }
//
//    @AfterAll
//    static void stopServer() throws Exception {
//        if (server != null) {
//            server.stop();
//            logger.info("Test server finalized.");
//        }
//
//        if (dataSource instanceof AutoCloseable ds) {
//            ds.close();
//            System.out.println("DataSource closed");
//        }
//    }
//}
