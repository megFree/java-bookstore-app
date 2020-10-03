package com.bookstore.services.orderservice;

import com.bookstore.dao.GenericDao;
import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.dao.mysql.OrderDaoMySql;
import com.bookstore.models.Order;
import com.bookstore.util.annotations.InjectDao;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.OrderSort;
import com.bookstore.util.enums.OrderStatus;
import com.bookstore.util.exceptions.OrderServiceException;
import com.bookstore.util.helpers.OrderComparator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class OrderService {
    @InjectDao(daoClass = OrderDaoMySql.class)
    private GenericDao<Order> dao;

    /**
     * Pushes new order to database
     *
     * @param name  customer name
     * @param email customer email
     * @throws DaoMySqlException dao error
     */
    public Order createOrder(String name, String email, double price) throws DaoMySqlException {
        return dao.create(new Order(name, email, price));
    }

    /**
     * Cancel order by id
     *
     * @param orderId order id
     * @throws OrderServiceException order already cancelled
     * @throws DaoMySqlException     dao error
     */
    public void cancelOrder(int orderId) throws OrderServiceException, DaoMySqlException {
        Order orderToCancel = dao.getById(orderId);
        if (orderToCancel.getStatus() == OrderStatus.CANCELLED) {
            throw new OrderServiceException("Order cancelled already");
        }
        orderToCancel.setStatus(OrderStatus.CANCELLED);
        dao.update(orderToCancel);
    }

    /**
     * Close order by id
     *
     * @param orderId order id
     * @throws DaoMySqlException dao error
     */
    public void closeOrder(int orderId) throws DaoMySqlException {
        Order order = dao.getById(orderId);
        order.setClosedDate(new Date());
        order.setStatus(OrderStatus.CLOSED);
        dao.update(order);
    }

    /**
     * Get sorted order list
     *
     * @param orderList raw order list
     * @param sort      sort type
     * @return sorted order list
     */
    public List<Order> sortOrderList(List<Order> orderList, OrderSort sort) {
        orderList.sort(new OrderComparator(sort));
        return orderList;
    }

    /**
     * Get all closed orders
     *
     * @return orderList
     * @throws DaoMySqlException dao error
     */
    public List<Order> getClosedOrders() throws DaoMySqlException {
        List<Order> orders = dao.getAll();
        List<Order> closedOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.CLOSED) {
                closedOrders.add(order);
            }
        }

        return closedOrders;
    }

    /**
     * Get closed orders in period
     *
     * @param from date
     * @param to   date
     * @return order list
     * @throws OrderServiceException wrong period
     * @throws DaoMySqlException     dao error
     */
    public List<Order> getClosedOrders(Date from, Date to) throws OrderServiceException, DaoMySqlException {

        if (from.after(to)) {
            throw new OrderServiceException("Invalid period");
        }

        List<Order> closedOrders = getClosedOrders();
        List<Order> closedOrdersInPeriod = new ArrayList<>();

        for (Order order : closedOrders) {
            boolean isInPeriod = order.getClosedDate().before(to)
                    && order.getClosedDate().after(from);

            if (isInPeriod) {
                closedOrdersInPeriod.add(order);
            }
        }

        return closedOrdersInPeriod;
    }

    /**
     * Get order by ID
     *
     * @param id id
     * @return order object
     * @throws DaoMySqlException dao error
     */
    public Order getOrderById(int id) throws DaoMySqlException {
        return dao.getById(id);
    }

    /**
     * Get all orders
     *
     * @param sort sort type
     * @return order list
     * @throws DaoMySqlException dao error
     */
    public List<Order> getOrders(OrderSort sort) throws DaoMySqlException {
        return sortOrderList(dao.getAll(), sort);
    }

    /**
     * Set order status by id
     *
     * @param orderID   order id
     * @param newStatus new status
     * @throws DaoMySqlException dao error
     */
    public void setOrderStatus(int orderID, OrderStatus newStatus) throws DaoMySqlException {
        if (newStatus == OrderStatus.CLOSED) {
            closeOrder(orderID);
            return;
        }

        Order order = dao.getById(orderID);
        order.setStatus(newStatus);
        dao.update(order);
    }

    /**
     * Get all orders
     *
     * @return order list
     * @throws DaoMySqlException dao error
     */
    public List<Order> getOrders() throws DaoMySqlException {
        return dao.getAll();
    }
}
