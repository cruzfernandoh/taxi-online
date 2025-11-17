package org.taxionline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.infra.dao.DataSourceManagerAdapter;
import org.taxionline.infra.di.BeanRegistry;
import org.taxionline.infra.javalin.JavalinConfig;
import org.taxionline.infra.mediator.Mediator;
import org.taxionline.domain.business.payment.ProcessPaymentBusiness;
import org.taxionline.domain.entity.event.RideCompletedEvent;
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

        var adapter = new DataSourceManagerAdapter();
        adapter.init(config);

        var mediator = new Mediator();
        mediator.register(RideCompletedEvent.eventName, (data) -> new ProcessPaymentBusiness().processPayment((RideCompletedEvent) data));

        var registry = new BeanRegistry();
        registry.registerAllBeans(adapter, mediator);
        registry.injectDependencies();

        var app = JavalinConfig.createJavalin();
        JavalinConfig.registerExceptions(app);
        JavalinConfig.registerEndpoints(app, registry);
        app.start("0.0.0.0", config.getServer().getPort());
        logger.info("Server started on http://localhost:{}/api", config.getServer().getPort());
    }


}
