package com.bookstore.util.core;

import com.bookstore.util.annotations.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ObjectFactory factory;
    private Map<Class, Object> singletonCache = new ConcurrentHashMap<>();
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public Config getConfig() {
        return config;
    }

    public <T> T getObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (singletonCache.containsKey(type)) {
            return (T) singletonCache.get(type);
        }

        if (implClass.isInterface()) {
            try {
                implClass = config.getImplClass(type);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            singletonCache.put(type, t);
        }

        return t;
    }
}
