package com.bookstore.util.core;

public class Application {
    public static ApplicationContext run(String packageToScan) {
        JavaConfig config = new JavaConfig(packageToScan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
