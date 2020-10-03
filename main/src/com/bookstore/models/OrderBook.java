package com.bookstore.models;

public class OrderBook implements Identified {
    private int bookId;
    private int orderId;

    public OrderBook() {
    }

    public OrderBook(int bookId, int orderId) {
        this.bookId = bookId;
        this.orderId = orderId;
    }

    @Override
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
