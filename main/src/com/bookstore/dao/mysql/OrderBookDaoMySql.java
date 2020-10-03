package com.bookstore.dao.mysql;

import com.bookstore.models.OrderBook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderBookDaoMySql extends AbstractDaoMySql<OrderBook> {
    @Override
    public String getCreateQuery() {
        return "INSERT INTO orderbooks (orderId, bookId) VALUES (?, ?)";
    }

    @Override
    public void prepareStatementForCreate(PreparedStatement statement, OrderBook obj) throws DaoMySqlException {
        try {
            statement.setInt(1, obj.getOrderId());
            statement.setInt(2, obj.getBookId());
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, OrderBook obj) throws DaoMySqlException {
        prepareStatementForCreate(statement, obj);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM orderbooks";
    }

    @Override
    public List<OrderBook> parseResultSet(ResultSet rs) throws DaoMySqlException {
        List<OrderBook> dependencies = new ArrayList<>();
        try {
            while (rs.next()) {
                OrderBook orderBook = new OrderBook();
                orderBook.setOrderId(rs.getInt(1));
                orderBook.setBookId(rs.getInt(2));
                dependencies.add(orderBook);
            }
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
        return dependencies;
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE orderbooks SET orderId = ?, bookId = ?";
    }
}
