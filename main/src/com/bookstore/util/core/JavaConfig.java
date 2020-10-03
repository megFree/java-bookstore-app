package com.bookstore.util.core;

import com.bookstore.util.core.scanner.ImplScanner;
import com.bookstore.util.core.scanner.Scanner;

public class JavaConfig implements Config {
    private Scanner scanner;

    public JavaConfig(String packageToScan) {
        this.scanner = new ImplScanner(packageToScan);
    }

    @Override
    public <T> Class getImplClass(Class<T> ifc) {
        Class implClass = null;
        try {
            implClass = scanner.getImplClass(ifc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return implClass;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
