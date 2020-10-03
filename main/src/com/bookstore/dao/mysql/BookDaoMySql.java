package com.bookstore.dao.mysql;

import com.bookstore.models.Book;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.helpers.DateFormatter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class BookDaoMySql extends AbstractDaoMySql<Book> {
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM books";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE books " +
                "SET title = ?, author = ?, isInStock = ?, publishDate = ?, price = ?, incomeDate = ?, description = ? " +
                "WHERE id = ?";
    }

    @Override
    public void prepareStatementForCreate(PreparedStatement statement, Book obj) {
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Book obj) throws DaoMySqlException {
        try {
            java.util.Date publishDate = obj.getPublishDate();
            Date publishSqlDate = DateFormatter.toSqlDate(publishDate);

            java.util.Date incomeDate = obj.getIncomeDate();
            Date incomeSqlDate = DateFormatter.toSqlDate(incomeDate);

            statement.setInt(8, obj.getId());
            statement.setString(1, obj.getTitle());
            statement.setString(2, obj.getAuthor());
            statement.setBoolean(3, obj.isInStock());
            statement.setDate(4, publishSqlDate);
            statement.setDouble(5, obj.getPrice());
            statement.setDate(6, incomeSqlDate);
            statement.setString(7, obj.getDescription());
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public List<Book> parseResultSet(ResultSet rs) throws DaoMySqlException {
        List<Book> books = new ArrayList<>();
        try {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setInStock(rs.getBoolean(4));
                book.setPublishDate(rs.getDate(5));
                book.setPrice(rs.getDouble(6));
                book.setIncomeDate(rs.getDate(7));
                book.setDescription(rs.getString(8));
                books.add(book);
            }
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
        return books;
    }
}
