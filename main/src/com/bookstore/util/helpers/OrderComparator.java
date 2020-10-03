package com.bookstore.util.helpers;

import com.bookstore.models.Order;
import com.bookstore.util.enums.OrderSort;
import com.bookstore.util.enums.OrderStatus;

import java.util.Comparator;
import java.util.Date;

public class OrderComparator implements Comparator<Order> {
    private OrderSort sort;

    public OrderComparator(OrderSort sort) {
        this.sort = sort;
    }

    @Override
    public int compare(Order order1, Order order2) {
        int result;
        switch (sort) {
            case STATUS:
                result = compareOrdersByStatus(order1, order2);
                break;
            case DATE_CLOSED:
                result = compareOrdersByDateClosed(order1, order2);
                break;
            default:
                result = compareOrdersByPrice(order1, order2);
        }
        return result;
    }

    private int compareOrdersByDateClosed(Order o1, Order o2) {
        Date firstDateClosed = o1.getClosedDate();
        Date secondDateClosed = o2.getClosedDate();

        boolean firstDateNull = false;
        if (firstDateClosed == null) {
            firstDateNull = true;
        }

        boolean secondDateNull = false;
        if (secondDateClosed == null) {
            secondDateNull = true;
        }

        if (firstDateNull && secondDateNull) {
            return 0;
        }

        if (firstDateNull) {
            return 1;
        }

        if (secondDateNull) {
            return -1;
        }

        if (firstDateClosed.after(secondDateClosed)) {
            return -1;
        }

        return 1;
    }

    private int compareOrdersByPrice(Order o1, Order o2) {
        double firstPrice = o1.getPrice();
        double secondsPrice = o2.getPrice();

        return Double.compare(secondsPrice, firstPrice);
    }

    private int compareOrdersByStatus(Order o1, Order o2) {
        OrderStatus firstStatus = o1.getStatus();
        OrderStatus secondStatus = o2.getStatus();

        int numericFirstStatus = statusToNumeric(firstStatus);
        int numericSecondStatus = statusToNumeric(secondStatus);

        return Integer.compare(numericFirstStatus, numericSecondStatus);
    }

    private int statusToNumeric(OrderStatus status) {
        int numericStatus;
        switch (status) {
            case NEW:
                numericStatus = 0;
                break;
            case CLOSED:
                numericStatus = 1;
                break;
            default:
                numericStatus = 2;
        }
        return numericStatus;
    }
}
