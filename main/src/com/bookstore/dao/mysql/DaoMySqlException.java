package com.bookstore.dao.mysql;

public class DaoMySqlException extends Exception {
    public DaoMySqlException(Throwable cause) {
        super(cause);
    }

    public DaoMySqlException(String message) {
        super(message);
    }
}
