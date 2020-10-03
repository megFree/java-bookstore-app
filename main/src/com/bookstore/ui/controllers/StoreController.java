package com.bookstore.ui.controllers;

import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.models.Book;
import com.bookstore.models.Order;
import com.bookstore.models.Request;
import com.bookstore.services.store.Store;
import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.BookSort;
import com.bookstore.util.enums.OrderSort;
import com.bookstore.util.enums.OrderStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class StoreController {
    @InjectObject
    private Store store;

    public void createRequest() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Creating request (q for quit)");
            System.out.println("Enter book ID:");

            String strInput = in.readLine();

            if (strInput.toLowerCase().charAt(0) == 'q') {
                System.out.println("Cancelled");
                return;
            }

            int bookID = Integer.parseInt(strInput);

            store.createRequest(bookID);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createOrder() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Creating order (q for quit)");

            int step = 0;


            String name = null;
            String mail = null;
            int bookID = 0;

            while (step < 3) {
                switch (step) {
                    case 0:
                        System.out.println("Enter customer name:");
                        break;
                    case 1:
                        System.out.println("Enter customer mail:");
                        break;
                    case 2:
                        System.out.println("Enter book id:");
                        break;
                }

                String strInput = in.readLine();

                if (strInput.toLowerCase().charAt(0) == 'q') {
                    System.out.println("Cancelled");
                    return;
                }

                switch (step) {
                    case 0:
                        name = strInput;
                        break;
                    case 1:
                        mail = strInput;
                        break;
                    case 2:
                        bookID = Integer.parseInt(strInput);
                        break;
                }

                step++;

            }

            store.createOrder(bookID, name, mail);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void cancelOrder() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Cancelling order (q for quit)");
            System.out.println("Enter order ID:");

            String strInput = in.readLine();

            if (strInput.toLowerCase().charAt(0) == 'q') {
                System.out.println("Cancelled");
                return;
            }

            int orderID = Integer.parseInt(strInput);

            store.cancelOrder(orderID);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void changeOrderStatus(OrderStatus newOrderStatus) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Changing order status to " + newOrderStatus.name().toLowerCase() + " (q for quit)");
            System.out.println("Enter order ID:");

            String strInput = in.readLine();

            if (strInput.toLowerCase().charAt(0) == 'q') {
                System.out.println("Cancelled");
                return;
            }

            int orderID = Integer.parseInt(strInput);

            store.changeOrderStatus(orderID, newOrderStatus);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void printClosedOrdersInPeriod(OrderSort sort) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Printing closed orders in period (q for quit)");

            int step = 0;
            Date firstDate = null;
            Date secondDate = null;

            while (step < 2) {

                switch (step) {
                    case 0:
                        System.out.println("Enter first date in dd-MM-yyyy:");
                        break;
                    case 1:
                        System.out.println("Enter second date in dd-MM-yyyy");
                        break;
                }

                String strInput = in.readLine();

                if (strInput.toLowerCase().charAt(0) == 'q') {
                    System.out.println("Cancelled");
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                switch (step) {
                    case 0:
                        firstDate = sdf.parse(strInput);
                        break;
                    case 1:
                        secondDate = sdf.parse(strInput);
                        break;
                }

                step++;
            }

            List<Order> closedOrders = store.getClosedOrdersByCost(firstDate, secondDate, sort);

            for (Order order : closedOrders) {
                System.out.println(order.getOrderId() + ". " + order.getCustomerMail() +
                        ", total price: " + store.getOrderIncome(order.getOrderId()) +
                        ", date closed: " + order.getClosedDate());
            }

        } catch (ParseException e) {
            System.out.println("Invalid input");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printIncomeInPeriod() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Printing income in period (q for quit)");

            int step = 0;
            Date firstDate = null;
            Date secondDate = null;

            while (step < 2) {

                switch (step) {
                    case 0:
                        System.out.println("Enter first date in dd-MM-yyyy:");
                        break;
                    case 1:
                        System.out.println("Enter second date in dd-MM-yyyy");
                        break;
                }

                String strInput = in.readLine();

                if (strInput.toLowerCase().charAt(0) == 'q') {
                    System.out.println("Cancelled");
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                switch (step) {
                    case 0:
                        firstDate = sdf.parse(strInput);
                        break;
                    case 1:
                        secondDate = sdf.parse(strInput);
                        break;
                }

                step++;
            }

            double income = store.getPeriodIncome(firstDate, secondDate);

            System.out.println("Income: " + income);

        } catch (ParseException ex) {
            System.out.println("Invalid input");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addBook() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Adding book to store (q for quit)");
            System.out.println("Enter ID of book to make available: ");
            String strInput = in.readLine();

            if (strInput.toLowerCase().charAt(0) == 'q') {
                System.out.println("Cancelled");
                return;
            }

            int bookID = Integer.parseInt(strInput);
            store.setBookAvailable(bookID);

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }

    public void printAmountClosedOrders() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Printing amount of closed orders in period (q for quit)");

            int step = 0;
            Date firstDate = null;
            Date secondDate = null;

            while (step < 2) {

                switch (step) {
                    case 0:
                        System.out.println("Enter first date in dd-MM-yyyy:");
                        break;
                    case 1:
                        System.out.println("Enter second date in dd-MM-yyyy");
                        break;
                }

                String strInput = in.readLine();

                if (strInput.toLowerCase().charAt(0) == 'q') {
                    System.out.println("Cancelled");
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                switch (step) {
                    case 0:
                        firstDate = sdf.parse(strInput);
                        break;
                    case 1:
                        secondDate = sdf.parse(strInput);
                        break;
                }

                step++;
            }

            int income = store.getAmountOfClosedOrders(firstDate, secondDate);

            System.out.println("Amount of closed orders: " + income);

        } catch (ParseException ex) {
            System.out.println("Invalid input");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printOrderDetails() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Printing order details (q for quit)");
            System.out.println("Enter ID of order: ");

            String strInput = in.readLine();

            if (strInput.toLowerCase().charAt(0) == 'q') {
                System.out.println("Cancelled");
                return;
            }

            int orderID = Integer.parseInt(strInput);
            Order order = store.getOrderByID(orderID);

            System.out.println("Customer name: " + order.getCustomerName() + ", customer mail: "
                    + order.getCustomerMail() + ", total price: " + store.getOrderIncome(orderID));

            List<Integer> booksIDs = store.getBooksIdsInOrder(orderID);
            List<Book> books = new ArrayList<>();

            for (Integer bookID : booksIDs) {
                Book book = store.getBookByID(bookID);
                books.add(book);
            }

            System.out.println("Books: ");
            for (Book book : books) {
                System.out.println(book.getTitle() + " - " + book.getTitle() + ", price: " + book.getPrice());
            }

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printBookDesc() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Printing book description (q for quit)");
            System.out.println("Enter ID of book: ");

            String strInput = in.readLine();

            if (strInput.toLowerCase().charAt(0) == 'q') {
                System.out.println("Cancelled");
                return;
            }

            int bookID = Integer.parseInt(strInput);
            Book book = store.getBookByID(bookID);

            System.out.println(book.getDescription());

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printBooksAlphabetically() {
        try {
            List<Book> books = store.getBooksAlphabetically();
            for (Book book : books) {
                System.out.println(book.getOrderId() + ". " + book.getTitle() + " - " + book.getAuthor());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printBooksByCost() {
        try {
            List<Book> books = store.getBooksSortByCost();
            for (Book book : books) {
                System.out.println(book.getOrderId() + ". " + book.getTitle() + " - " + book.getAuthor()
                        + ", price: " + book.getPrice());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printBooksByAvailability() {
        try {
            List<Book> books = store.getBooksSortByAvailability();
            for (Book book : books) {
                System.out.println(book.getOrderId() + ". " + book.getTitle() + " - " + book.getAuthor()
                        + ", is available: " + book.isInStock());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printBooksByPublishDate() {
        try {
            List<Book> books = store.getBooksSortByPubDate();
            for (Book book : books) {
                System.out.println(book.getOrderId() + ". " + book.getTitle() + " - " + book.getAuthor()
                        + ", pub date: " + book.getPublishDate());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printStaleBooksByCost() {
        try {
            List<Book> staleBooks = store.getStaleBooks(BookSort.PRICE);
            for (Book book : staleBooks) {
                System.out.println(book.getOrderId() + ". " + book.getTitle() +
                        ", price: " + book.getPrice());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printOrdersByDateClosed() {
        try {
            List<Order> orderList = store.getOrdersSortByClosedDate();
            for (Order order : orderList) {
                System.out.println(order.getOrderId() + ". " + order.getCustomerMail()
                        + ", closed date: " + order.getClosedDate());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printOrdersByCost() {
        try {
            List<Order> orderList = store.getOrdersSortByCost();
            for (Order order : orderList) {
                System.out.println(order.getOrderId() + ". " + order.getCustomerMail()
                        + ", total price: " + store.getOrderIncome(order.getOrderId()));
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printOrdersByStatus() {
        try {
            List<Order> orderList = store.getOrdersSortByStatus();
            for (Order order : orderList) {
                System.out.println(order.getOrderId() + ". " + order.getCustomerMail()
                        + ", status: " + order.getStatus().toString().toLowerCase());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printRequestsByAmount() {
        try {
            List<Request> requests = store.getRequestsSortedAmount();
            for (Request request : requests) {
                Book book = store.getBookByID(request.getBookId());
                String bookTitle = book.getTitle();
                System.out.println(request.getOrderId() + ". book: " + bookTitle
                        + ", requests on this book: " + store.getAmountRequestsOnBook(book.getOrderId())
                        + ", is closed: " + request.isClosed());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printRequestsAlphabetically() {
        try {
            List<Request> requests = store.getRequestsSortedAlphabetically();
            for (Request request : requests) {
                Book book = store.getBookByID(request.getBookId());
                String bookTitle = book.getTitle();
                System.out.println(request.getOrderId() + ". book: " + bookTitle);
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }

    public void printStaleBooksByIncomeDate() {
        try {
            List<Book> staleBooks = store.getStaleBooks(BookSort.INCOME_DATE);
            for (Book book : staleBooks) {
                System.out.println(book.getOrderId() + ". " + book.getTitle() +
                        ", income date: " + book.getIncomeDate());
            }
        } catch (DaoMySqlException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println();
        }
    }
}
