package org.taxionline.di;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanRegistry {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public <T> void registerBean(Class<T> type, T instance) {
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

    public void injectInto(Object bean) {
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
