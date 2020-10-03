package com.bookstore.dao.mysql;

import com.bookstore.models.Order;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.OrderStatus;
import com.bookstore.util.helpers.DateFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderDaoMySql extends AbstractDaoMySql<Order> {
    @Override
    public String getCreateQuery() {
        return "INSERT INTO orders (customerName, customerMail, closedDate, status, totalPrice) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public void prepareStatementForCreate(PreparedStatement statement, Order obj) throws DaoMySqlException {
        try {
            java.util.Date closedDate = obj.getClosedDate();

            statement.setString(1, obj.getCustomerName());
            statement.setString(2, obj.getCustomerMail());
            statement.setDate(3, closedDate == null ? null : DateFormatter.toSqlDate(closedDate));
            statement.setString(4, obj.getStatus().toString());
            statement.setDouble(5, obj.getPrice());
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Order obj) throws DaoMySqlException {
        try {
            java.util.Date closedDate = obj.getClosedDate();

            statement.setString(1, obj.getCustomerName());
            statement.setString(2, obj.getCustomerMail());
            statement.setDate(3, closedDate == null ? null : DateFormatter.toSqlDate(closedDate));
            statement.setString(4, obj.getStatus().toString());
            statement.setDouble(5, obj.getPrice());
            statement.setInt(6, obj.getOrderId());
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM orders";
    }

    @Override
    public List<Order> parseResultSet(ResultSet rs) throws DaoMySqlException {
        List<Order> parsedOrderList = new ArrayList<>();
        try {
            while (rs.next()) {
                Order tmpOrder = new Order();
                tmpOrder.setId(rs.getInt(1));
                tmpOrder.setCustomerName(rs.getString(2));
                tmpOrder.setCustomerMail(rs.getString(3));
                tmpOrder.setClosedDate(rs.getDate(4));
                tmpOrder.setStatus(Enum.valueOf(OrderStatus.class, rs.getString(5)));
                tmpOrder.setPrice(rs.getDouble(6));
                parsedOrderList.add(tmpOrder);
            }
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }

        return parsedOrderList;
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE orders " +
                "SET customerName = ?, customerMail = ?, closedDate = ?, status = ?, totalPrice = ? " +
                "WHERE id = ?";
    }
}
