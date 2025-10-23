package base;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.config.dao.CustomPersistenceUnitInfo;
import org.taxionline.config.dao.DataSourceManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSourceManagerTest implements DataSourceManager {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceManagerTest.class);

    EntityManagerFactory emf;

    @Override
    public void init() {
        try {
            if (logger.isInfoEnabled())
                logger.info("Initializing DatasourceManager ...");

            AgroalDataSourceConfiguration configuration = new AgroalDataSourceConfigurationSupplier()
                    .connectionPoolConfiguration(cp -> cp
                            .maxSize(5)
                            .minSize(1)
                            .initialSize(1)
                            .connectionFactoryConfiguration(cf -> cf
                                    .jdbcUrl("jdbc:h2:mem:default")
                                    .connectionProviderClassName("org.h2.Driver")
                                    .principal(new NamePrincipal("test_user"))
                            )
                    ).get();

            AgroalDataSource dataSource = AgroalDataSource.from(configuration);

            Map<String, Object> props = buildHibernatePersistenceProviderProps(dataSource);
            emf = new org.hibernate.jpa.HibernatePersistenceProvider()
                    .createContainerEntityManagerFactory(
                            new CustomPersistenceUnitInfo("default"),
                            props
                    );
        } catch (SQLException e) {
            logger.error("Error initializing DataSourceManager: ", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Unexpected error initializing DataSourceManager: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private Map<String, Object> buildHibernatePersistenceProviderProps(AgroalDataSource dataSource) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.connection.datasource", dataSource);
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.session_factory_name", "");
        props.put("hibernate.session_factory_name_is_jndi", "false");
        return props;
    }
}
