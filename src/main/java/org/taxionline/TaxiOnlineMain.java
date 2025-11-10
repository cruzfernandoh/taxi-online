package org.taxionline;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.config.dao.DataSourceManagerAdapter;
import org.taxionline.config.di.BeanRegistry;
import org.taxionline.config.javalin.JavalinConfig;
import org.taxionline.util.AppConfigUtils;

public class TaxiOnlineMain {
    private static final Logger logger = LoggerFactory.getLogger(TaxiOnlineMain.class);

    public static void main(String[] args) {
        AppConfigUtils config = AppConfigUtils.getInstance();
        logger.info("Application config success loaded: {}", config.get("app.name"));

        DataSourceManager adapter = new DataSourceManagerAdapter();
        adapter.init();
        BeanRegistry registry = new BeanRegistry();
        registry.registerAllBeans(adapter);
        registry.injectDependencies();

        Javalin app = JavalinConfig.createJavalin();
        JavalinConfig.registerExceptions(app);
        JavalinConfig.registerEndpoints(app, registry);
        app.start("0.0.0.0", Integer.parseInt(config.get("server.port")));
        logger.info("Server started on http://localhost:{}/api", config.get("server.port"));
    }


}
