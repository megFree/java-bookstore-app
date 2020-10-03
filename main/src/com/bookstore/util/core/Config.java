package com.bookstore.util.core;

import com.bookstore.util.core.scanner.Scanner;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> ifc) throws ClassNotFoundException;

    Scanner getScanner();
}
