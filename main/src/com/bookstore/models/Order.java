package com.bookstore.models;

import com.bookstore.util.enums.OrderStatus;

import java.util.Date;

public class Order implements Identified {
    private Integer id;
    private String customerName;
    private String customerMail;
    private Date closedDate;
    private OrderStatus status = OrderStatus.NEW;
    private double price;

    public Order() {
    }

    public Order(String customerName, String customerMail, double price) {
        this.customerName = customerName;
        this.customerMail = customerMail;
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
