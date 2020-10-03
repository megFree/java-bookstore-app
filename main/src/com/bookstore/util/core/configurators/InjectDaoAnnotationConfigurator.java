package com.bookstore.util.core.configurators;

import com.bookstore.util.annotations.InjectDao;
import com.bookstore.util.core.ApplicationContext;

import java.lang.reflect.Field;

public class InjectDaoAnnotationConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectDao.class)) {
                Class<?> daoType = field.getAnnotation(InjectDao.class).daoClass();
                try {
                    field.setAccessible(true);
                    field.set(obj, context.getObject(daoType));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
