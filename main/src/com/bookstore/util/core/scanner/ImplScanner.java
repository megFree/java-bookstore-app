package com.bookstore.util.core.scanner;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ImplScanner implements Scanner {
    private static String packageToScan;
    private String relativePath;

    public ImplScanner(String packageToScan) {
        ImplScanner.packageToScan = packageToScan;
        this.relativePath = packageToScan;

    }

    /**
     * Recursively search implementation for ifc
     *
     * @param ifc interface or class
     * @param <T> type
     * @return implementation
     * @throws ClassNotFoundException
     */
    @Override
    public <T> Class<? extends T> getImplClass(Class<? extends T> ifc) throws ClassNotFoundException {
        List<Class<? extends T>> typeList = getSubTypesOf(ifc);
        if (typeList.size() != 1) {
            throw new RuntimeException("0 or more than 1 implementations for " + ifc.getName() + " found");
        }
        return getSubTypesOf(ifc).iterator().next();
    }

    /**
     * Recursively search implementations of ifc
     *
     * @param ifc interface or class
     * @param <T> type
     * @return list of implementations
     * @throws ClassNotFoundException
     */
    @Override
    public <T> List<Class<? extends T>> getSubTypesOf(Class<? extends T> ifc) throws ClassNotFoundException {
        File[] files = getFilesInPackage(relativePath);
        List<Class<? extends T>> classList = new ArrayList<>();
        for (File file : files) {
            relativePath = getFileRelativePath(file);
            if (file.isDirectory()) {
                List<Class<? extends T>> implClass = getSubTypesOf(ifc);
                if (implClass == null) {
                    continue;
                }
                classList.addAll(implClass);
            } else {
                if (file.getName().endsWith(".class")) {
                    String classFullName = (relativePath.replace("/", ".") + "." + file.getName())
                            .replaceAll(".class", "");
                    Class<?> cls = Class.forName(classFullName);
                    if (cls.isInterface()) {
                        continue;
                    }
                    if (ifc.isAssignableFrom(cls)) {
                        classList.add((Class<? extends T>) cls);
                    }
                }
            }
        }

        if (classList.size() == 0) {
            return null;
        }

        resetRelativePath();

        return classList;
    }

    private File[] getFilesInPackage(String path) {
        URL root = Thread.currentThread().getContextClassLoader().getResource(path.replaceAll("[\\\\.]", "/"));
        File[] files;
        if (root != null) {
            files = new File(URLDecoder.decode(root.getFile(), StandardCharsets.UTF_8)).listFiles();
        } else {
            throw new RuntimeException("Package not found");
        }

        if (files == null || files.length == 0) {
            throw new RuntimeException("Package is empty");
        }

        return files;
    }

    private void resetRelativePath() {
        this.relativePath = packageToScan;
    }

    private String getFileRelativePath(File file) {
        String tmpPath = file.getPath().replace("\\", "/");
        String[] folders = tmpPath.split("/");
        List<String> relativeFolders = new ArrayList<>();
        for (int i = 0; i < folders.length; i++) {
            if (folders[i].equals("com")) {
                for (int j = i; j < folders.length; j++) {
                    if (folders[j].endsWith(".class")) {
                        continue;
                    }
                    relativeFolders.add(folders[j]);
                }
            }
        }
        String resultPath = "";
        for (String relativeFolder : relativeFolders) {
            resultPath = resultPath.concat(relativeFolder + "/");
        }
        resultPath = resultPath.replaceAll("/$", "");

        return resultPath;
    }
}
