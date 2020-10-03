package com.bookstore.util.core.configurators;

import com.bookstore.services.store.Store;
import com.bookstore.util.core.ApplicationContext;

public class StoreConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> objClass = obj.getClass();
        if (Store.class.isAssignableFrom(objClass)) {

        }
    }
}
