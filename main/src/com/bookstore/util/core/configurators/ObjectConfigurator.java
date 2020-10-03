package com.bookstore.util.core.configurators;

import com.bookstore.util.core.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object obj, ApplicationContext context);
}
