package com.bookstore.util.core.configurators;

import com.bookstore.dao.mysql.AbstractDaoMySql;
import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.core.ApplicationContext;

import java.lang.reflect.Field;

public class MySqlConnectionConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> objClass = obj.getClass();
        if (AbstractDaoMySql.class.isAssignableFrom(objClass)) {
            Class<?> superclass = objClass.getSuperclass();
            Field[] declaredFields = superclass.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(InjectObject.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(obj, context.getObject(field.getType()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
