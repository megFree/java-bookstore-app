package com.bookstore.util.helpers;

import com.bookstore.models.Book;
import com.bookstore.models.Request;
import com.bookstore.util.enums.RequestSort;

import java.util.Comparator;
import java.util.List;

public class RequestComparator implements Comparator<Request> {
    private RequestSort sort;
    private List<Request> requests;
    private List<Book> books;

    public RequestComparator(RequestSort sort, List<Request> requests, List<Book> books) {
        this.sort = sort;
        this.requests = requests;
        this.books = books;
    }

    @Override
    public int compare(Request r1, Request r2) {
        int result;
        switch (sort) {
            case ALPHABETICAL:
                result = compareRequestsByBookTitle(r1, r2);
                break;
            default:
                result = compareRequestsByAmount(r1, r2);
        }

        return result;
    }

    private int compareRequestsByAmount(Request r1, Request r2) {
        int firstBookId = r1.getBookId();
        int secondBookId = r2.getBookId();

        if (firstBookId == secondBookId) {
            return 0;
        }

        int firstBookIdAmount = 0;
        int secondBookIdAmount = 0;

        for (Request request : requests) {
            int requestId = request.getBookId();

            if (requestId == firstBookId) {
                firstBookIdAmount++;
            }

            if (requestId == secondBookId) {
                secondBookIdAmount++;
            }
        }

        return Integer.compare(secondBookIdAmount, firstBookIdAmount);
    }

    private int compareRequestsByBookTitle(Request r1, Request r2) {
        String firstTitle = null;
        for (Book book : books) {
            if (r1.getBookId() == book.getOrderId()) {
                firstTitle = book.getTitle();
                break;
            }
        }

        String secondsTitle = null;
        for (Book book : books) {
            if (r2.getBookId() == book.getOrderId()) {
                secondsTitle = book.getTitle();
                break;
            }
        }

        if (firstTitle != null && secondsTitle != null) {
            if (firstTitle.toLowerCase().compareTo(secondsTitle.toLowerCase()) < 0) {
                return -1;
            }
        }

        return 1;
    }


}
