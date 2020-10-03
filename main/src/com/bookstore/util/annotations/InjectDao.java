package com.bookstore.util.annotations;

import com.bookstore.dao.GenericDao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectDao {
    Class<? extends GenericDao<?>> daoClass();
}
