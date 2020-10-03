package com.bookstore.util.helpers;

import com.bookstore.models.Book;
import com.bookstore.util.enums.BookSort;

import java.util.Comparator;
import java.util.Date;

public class BookComparator implements Comparator<Book> {
    private BookSort sort;

    public BookComparator(BookSort sort) {
        this.sort = sort;
    }

    @Override
    public int compare(Book book1, Book book2) {
        int result;
        switch (sort) {
            case PRICE:
                result = compareByPrice(book1, book2);
                break;
            case AVAILABLE:
                result = compareByAvailable(book1, book2);
                break;
            case PUBLISH_DATE:
                result = compareByPublishDate(book1, book2);
                break;
            case INCOME_DATE:
                result = compareByIncomeDate(book1, book2);
                break;
            default:
                result = compareByAlphabet(book1, book2);
        }
        return result;
    }

    private int compareByIncomeDate(Book book1, Book book2) {
        Date firstDate = book1.getIncomeDate();
        Date secondDate = book2.getIncomeDate();

        if (firstDate.before(secondDate)) {
            return -1;
        }

        return 1;
    }

    private int compareByAvailable(Book book1, Book book2) {
        boolean firstAvailable = book1.isInStock();
        boolean secondAvailable = book2.isInStock();

        boolean isFirstSmaller = firstAvailable && !secondAvailable;
        if (isFirstSmaller) {
            return -1;
        }

        return 1;
    }

    private int compareByAlphabet(Book book1, Book book2) {
        String firstTitle = book1.getTitle();
        String secondTitle = book2.getTitle();

        int stringComparingResult = firstTitle.toLowerCase().compareTo(secondTitle.toLowerCase());
        if (stringComparingResult < 0) {
            return -1;
        }

        return 1;
    }

    private int compareByPrice(Book book1, Book book2) {
        double firstPrice = book1.getPrice();
        double secondPrice = book2.getPrice();

        if (secondPrice < firstPrice) {
            return 1;
        }

        return -1;
    }

    private int compareByPublishDate(Book book1, Book book2) {
        Date firstDate = book1.getPublishDate();
        Date secondDate = book2.getPublishDate();

        if (secondDate.before(firstDate)) {
            return -1;
        }

        return 1;
    }
}
