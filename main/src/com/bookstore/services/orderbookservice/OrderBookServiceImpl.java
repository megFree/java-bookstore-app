package com.bookstore.services.orderbookservice;

import com.bookstore.dao.GenericDao;
import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.dao.mysql.OrderBookDaoMySql;
import com.bookstore.models.OrderBook;
import com.bookstore.util.annotations.InjectDao;
import com.bookstore.util.annotations.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderBookServiceImpl implements OrderBookService {
    @InjectDao(daoClass = OrderBookDaoMySql.class)
    private GenericDao<OrderBook> dao;

    @Override
    public void createDependency(int orderId, int bookId) throws DaoMySqlException {
        dao.create(new OrderBook(bookId, orderId));
    }

    @Override
    public List<OrderBook> getDependenciesByOrderId(int orderId) throws DaoMySqlException {
        List<OrderBook> dependencies = dao.getAll();
        List<OrderBook> orderDependencies = new ArrayList<>();
        for (OrderBook orderBook : dependencies) {
            if (orderBook.getOrderId() == orderId) {
                orderDependencies.add(orderBook);
            }
        }
        return orderDependencies;
    }
}
