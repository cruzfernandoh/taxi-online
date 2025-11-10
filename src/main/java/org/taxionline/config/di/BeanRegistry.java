package org.taxionline.config.di;

import org.taxionline.adapter.inboud.AccountResource;
import org.taxionline.adapter.inboud.RideResource;
import org.taxionline.adapter.outbound.account.AccountRepositoryAdapter;
import org.taxionline.adapter.outbound.ride.RideRepositoryAdapter;
import org.taxionline.config.dao.DataSourceManager;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.core.business.ride.RideBusiness;
import org.taxionline.mapper.account.AccountMapper;
import org.taxionline.mapper.ride.RideMapper;
import org.taxionline.port.outbound.account.AccountRepository;
import org.taxionline.port.outbound.ride.RideRepository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanRegistry {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public void registerAllBeans(DataSourceManager adapter) {
        registerBean(DataSourceManager.class, adapter);

        registerBean(AccountRepository.class, new AccountRepositoryAdapter());
        registerBean(AccountBusiness.class, new AccountBusiness());
        registerBean(AccountResource.class, new AccountResource());
        registerBean(AccountMapper.class, new AccountMapper());

        registerBean(RideRepository.class, new RideRepositoryAdapter());
        registerBean(RideBusiness.class, new RideBusiness());
        registerBean(RideResource.class, new RideResource());
        registerBean(RideMapper.class, new RideMapper());
    }

    private <T> void registerBean(Class<T> type, T instance) {
        beans.put(type, instance);
    }

    public void injectDependencies() {
        try {
            for (Object bean : beans.values()) {
                injectInto(bean);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inject bean", e);
        }
    }

    private void injectInto(Object bean) {
        Class<?> clazz = bean.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(BeanInjection.class)) {
                    Object dependency = beans.get(field.getType());
                    if (dependency != null) {
                        field.setAccessible(true);
                        try {
                            field.set(bean, dependency);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Failed to inject " + field.getName(), e);
                        }
                    } else {
                        throw new RuntimeException("No bean registered for type: " + field.getType());
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}
