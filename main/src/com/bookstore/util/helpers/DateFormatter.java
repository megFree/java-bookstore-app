package com.bookstore.util.helpers;

public class DateFormatter {
    private static final String pattern = "dd-MM-yyyy";

    public static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
}
