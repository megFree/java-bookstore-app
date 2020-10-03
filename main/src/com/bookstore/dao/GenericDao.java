package com.bookstore.dao;

import com.bookstore.dao.mysql.DaoMySqlException;

import java.util.List;

public interface GenericDao<T> {
    T create(T object) throws DaoMySqlException;

    T getById(int id) throws DaoMySqlException;

    List<T> getAll() throws DaoMySqlException;

    T update(T object) throws DaoMySqlException;
}
