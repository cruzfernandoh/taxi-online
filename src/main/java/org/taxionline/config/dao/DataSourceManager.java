package org.taxionline.config.dao;

import jakarta.persistence.EntityManager;

public interface DataSourceManager {

    EntityManager getEntityManager();

    void init();
}
