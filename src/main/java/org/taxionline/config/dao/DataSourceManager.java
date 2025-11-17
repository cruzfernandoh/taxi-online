package org.taxionline.config.dao;

import jakarta.persistence.EntityManager;
import org.taxionline.util.AppConfigUtils;

public interface DataSourceManager {

    EntityManager getEntityManager();

    void init(AppConfigUtils configUtils);
}
