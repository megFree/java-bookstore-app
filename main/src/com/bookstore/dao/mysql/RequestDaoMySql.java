package com.bookstore.dao.mysql;

import com.bookstore.models.Request;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.helpers.DateFormatter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RequestDaoMySql extends AbstractDaoMySql<Request> {
    @Override
    public String getCreateQuery() {
        return "INSERT INTO requests (dateCreated, isClosed, bookId) VALUES (?, ?, ?)";
    }

    @Override
    public void prepareStatementForCreate(PreparedStatement statement, Request obj) throws DaoMySqlException {
        try {
            Date nowDate = DateFormatter.toSqlDate(new java.util.Date());
            statement.setDate(1, nowDate);
            statement.setBoolean(2, false);
            statement.setInt(3, obj.getBookId());
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Request obj) throws DaoMySqlException {
        try {
            Date nowDate = DateFormatter.toSqlDate(new java.util.Date());
            statement.setDate(1, nowDate);
            statement.setBoolean(2, obj.isClosed());
            statement.setInt(3, obj.getBookId());
            statement.setInt(4, obj.getId());
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM requests";
    }

    @Override
    public List<Request> parseResultSet(ResultSet rs) throws DaoMySqlException {
        List<Request> parsedList = new ArrayList<>();
        try {
            while (rs.next()) {
                Request tempRequest = new Request();
                tempRequest.setId(rs.getInt(1));
                tempRequest.setDateCreated(rs.getDate(2));
                tempRequest.setClosed(rs.getBoolean(3));
                tempRequest.setBookId(rs.getInt(4));
                parsedList.add(tempRequest);
            }
        } catch (SQLException e) {
            throw new DaoMySqlException(e);
        }
        return parsedList;
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE requests SET dateCreated = ?, isClosed = ?, bookId = ? WHERE id = ?";
    }
}
