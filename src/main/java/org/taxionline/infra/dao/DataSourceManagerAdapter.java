package org.taxionline.infra.dao;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.util.AppConfigUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataSourceManagerAdapter implements DataSourceManager {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceManagerAdapter.class);

    EntityManagerFactory emf;

    @Override
    public void init(AppConfigUtils config) {
        try {
            if (logger.isInfoEnabled())
                logger.info("Initializing DatasourceManager ...");

            AgroalDataSourceConfiguration configuration = new AgroalDataSourceConfigurationSupplier()
                    .connectionPoolConfiguration(cp -> cp
                            .maxSize(config.getDatasource().getPool().getMaxSize())
                            .minSize(config.getDatasource().getPool().getMinSize())
                            .initialSize(config.getDatasource().getPool().getInitialSize())
                            .connectionFactoryConfiguration(cf -> cf
                                    .jdbcUrl(config.getDatasource().getUrl())
                                    .principal(new NamePrincipal(config.getDatasource().getUser()))
                                    .credential(new SimplePassword(config.getDatasource().getPassword()))
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
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.session_factory_name", "");
        props.put("hibernate.session_factory_name_is_jndi", "false");
        return props;
    }
}
