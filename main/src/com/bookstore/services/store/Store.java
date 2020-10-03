package com.bookstore.services.store;

import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.models.Book;
import com.bookstore.models.Order;
import com.bookstore.models.Request;
import com.bookstore.util.enums.BookSort;
import com.bookstore.util.enums.OrderSort;
import com.bookstore.util.enums.OrderStatus;
import com.bookstore.util.enums.RequestSort;
import com.bookstore.util.exceptions.BookServiceException;
import com.bookstore.util.exceptions.OrderServiceException;

import java.util.Date;
import java.util.List;

public interface Store {
    List<Book> getBooksSortByPubDate() throws DaoMySqlException;

    List<Book> getBooksAlphabetically() throws DaoMySqlException;

    List<Book> getBooksSortByCost() throws DaoMySqlException;

    List<Book> getBooksSortByAvailability() throws DaoMySqlException;

    List<Order> getOrdersSortByCost() throws DaoMySqlException;

    List<Order> getOrdersSortByStatus() throws DaoMySqlException;

    List<Order> getOrdersSortByClosedDate() throws DaoMySqlException;

    void createOrder(int bookID, String name, String email) throws DaoMySqlException, BookServiceException;

    void cancelOrder(int orderID) throws OrderServiceException, DaoMySqlException;

    void changeOrderStatus(int orderID, OrderStatus newStatus) throws DaoMySqlException;

    void createRequest(int bookID) throws DaoMySqlException, BookServiceException;

    Book getBookByID(int bookId) throws DaoMySqlException;

    List<Request> getRequests(RequestSort sort) throws DaoMySqlException;

    List<Request> getRequestsSortedAlphabetically() throws DaoMySqlException;

    List<Request> getRequestsSortedAmount() throws DaoMySqlException;

    int getAmountRequestsOnBook(int bookID);

    void setBookAvailable(int bookID) throws DaoMySqlException, BookServiceException;

    List<Order> getClosedOrdersByCost(Date from, Date to, OrderSort sort) throws DaoMySqlException, OrderServiceException;

    double getPeriodIncome(Date firstDate, Date secondDate) throws DaoMySqlException, OrderServiceException;

    int getAmountOfClosedOrders(Date firstDate, Date secondDate) throws DaoMySqlException, OrderServiceException;

    Order getOrderByID(int orderID) throws DaoMySqlException;

    List<Book> getBooks() throws DaoMySqlException;

    List<Request> getRequests() throws DaoMySqlException;

    List<Order> getOrders() throws DaoMySqlException;

    List<Book> getStaleBooks(BookSort sort) throws DaoMySqlException;

    List<Order> getClosedOrdersByCost() throws DaoMySqlException;

    double getOrderIncome(int orderId) throws DaoMySqlException;

    List<Integer> getBooksIdsInOrder(int orderId) throws DaoMySqlException;
}
