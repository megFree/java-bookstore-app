package com.bookstore.util.core.configurators;

import com.bookstore.util.annotations.ApplicationProperty;
import com.bookstore.util.core.ApplicationContext;
import com.bookstore.util.enums.ApplicationPropertyTypesEnum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private Map<String, String> propertiesMap;

    public ApplicationPropertyAnnotationObjectConfigurator() {
        String path = ClassLoader.getSystemClassLoader()
                .getResource("application.properties").getPath();

        try {
            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
            propertiesMap = lines.
                    map(line -> line.split("=")).
                    collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            ApplicationProperty annotation = field.getAnnotation(ApplicationProperty.class);

            if (annotation != null) {
                try {
                    String rawPropertyValue =
                            propertiesMap.get(annotation.propertyName().isEmpty() ? field.getName() : annotation.propertyName());

                    ApplicationPropertyTypesEnum type = annotation.type();

                    field.setAccessible(true);

                    switch (type) {
                        case INT:
                            field.set(obj, Integer.parseInt(rawPropertyValue));
                            break;
                        case BOOLEAN:
                            field.set(obj, Boolean.parseBoolean(rawPropertyValue));
                            break;
                        default:
                            field.set(obj, rawPropertyValue);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
