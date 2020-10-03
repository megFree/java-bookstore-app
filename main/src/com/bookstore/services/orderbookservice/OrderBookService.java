package com.bookstore.services.orderbookservice;

import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.models.OrderBook;

import java.util.List;

public interface OrderBookService {
    /**
     * Create dependency between order and book and pushes it into a database
     *
     * @param orderId order id
     * @param bookId  book id
     * @throws DaoMySqlException dao error
     */
    void createDependency(int orderId, int bookId) throws DaoMySqlException;

    /**
     * Gets books on order
     *
     * @param orderId order id
     * @return list of dependencies
     */
    List<OrderBook> getDependenciesByOrderId(int orderId) throws DaoMySqlException;
}
