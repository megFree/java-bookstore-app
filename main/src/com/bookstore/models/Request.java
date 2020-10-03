package com.bookstore.models;

import java.util.Date;

public class Request implements Identified {
    boolean isClosed = false;
    private Integer id;
    private int bookId;
    private Date dateCreated;

    public Request() {
    }

    public Request(int bookId, Date dateCreated) {
        this.bookId = bookId;
        this.dateCreated = dateCreated;
    }

    public Integer getOrderId() {
        return id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
