package org.taxionline;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.config.dao.DataSourceManagerAdapter;
import org.taxionline.config.di.BeanRegistry;
import org.taxionline.config.javalin.JavalinConfig;
import org.taxionline.config.mediator.Mediator;
import org.taxionline.core.business.payment.ProcessPaymentBusiness;
import org.taxionline.core.domain.event.RideCompletedEvent;
import org.taxionline.util.AppConfigUtils;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class TaxiOnlineMain {
    private static final Logger logger = LoggerFactory.getLogger(TaxiOnlineMain.class);

    public static void main(String[] args) {

        var yaml = new Yaml(new Constructor(AppConfigUtils.class, new LoaderOptions()));
        var config = (AppConfigUtils) yaml.load(TaxiOnlineMain.class.getClassLoader().getResourceAsStream("config.yaml"));
        logger.info("Application config success loaded: {}", config.getApp().getName());

        DataSourceManager adapter = new DataSourceManagerAdapter();
        adapter.init(config);

        Mediator mediator = new Mediator();
        mediator.register(RideCompletedEvent.eventName, (data) -> new ProcessPaymentBusiness().processPayment((RideCompletedEvent) data));

        BeanRegistry registry = new BeanRegistry();
        registry.registerAllBeans(adapter, mediator);
        registry.injectDependencies();

        Javalin app = JavalinConfig.createJavalin();
        JavalinConfig.registerExceptions(app);
        JavalinConfig.registerEndpoints(app, registry);
        app.start("0.0.0.0", config.getServer().getPort());
        logger.info("Server started on http://localhost:{}/api", config.getServer().getPort());
    }


}
