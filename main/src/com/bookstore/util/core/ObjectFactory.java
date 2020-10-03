package com.bookstore.util.core;

import com.bookstore.util.annotations.PostConstruct;
import com.bookstore.util.core.configurators.ObjectConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private ApplicationContext context;

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        try {
            for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T createObject(Class<T> implClass) {
        T result = null;

        try {
            T t = create(implClass);

            configure(t);

            postConstruct(implClass, t);

            result = t;
        } catch (InstantiationException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private <T> void postConstruct(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> {
            objectConfigurator.configure(t, context);
        });
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }


}
