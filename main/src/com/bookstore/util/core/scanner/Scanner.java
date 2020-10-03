package com.bookstore.util.core.scanner;

import java.util.List;

public interface Scanner {
    <T> Class<? extends T> getImplClass(Class<? extends T> ifc) throws ClassNotFoundException;

    <T> List<Class<? extends T>> getSubTypesOf(Class<? extends T> ifc) throws ClassNotFoundException;
}
