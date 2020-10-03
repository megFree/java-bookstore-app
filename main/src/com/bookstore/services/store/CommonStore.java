package com.bookstore.services.store;

import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.models.Book;
import com.bookstore.models.Order;
import com.bookstore.models.OrderBook;
import com.bookstore.models.Request;
import com.bookstore.services.bookservice.BookService;
import com.bookstore.services.orderbookservice.OrderBookService;
import com.bookstore.services.orderservice.OrderService;
import com.bookstore.services.requestservice.RequestService;
import com.bookstore.util.annotations.ApplicationProperty;
import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.*;
import com.bookstore.util.exceptions.BookServiceException;
import com.bookstore.util.exceptions.OrderServiceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
public class CommonStore implements Store {
    @ApplicationProperty(type = ApplicationPropertyTypesEnum.INT)
    private int monthsToStale;
    @ApplicationProperty(propertyName = "closeRequestOnAvailable", type = ApplicationPropertyTypesEnum.BOOLEAN)
    private boolean closeRequestsOnBookAdded;

    @InjectObject
    private BookService bookService;
    @InjectObject
    private OrderService orderService;
    @InjectObject
    private RequestService requestService;
    @InjectObject
    private OrderBookService orderBookService;

    /**
     * Get date sorted books from store
     *
     * @return sorted booklist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Book> getBooksSortByPubDate() throws DaoMySqlException {
        return bookService.getBooks(BookSort.PUBLISH_DATE);
    }

    /**
     * Get alphabetically sorted books from store
     *
     * @return sorted booklist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Book> getBooksAlphabetically() throws DaoMySqlException {
        return bookService.getBooks(BookSort.ALPHABETICAL);
    }

    /**
     * Get booklist sorted by price
     *
     * @return sorted booklist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Book> getBooksSortByCost() throws DaoMySqlException {
        return bookService.getBooks(BookSort.PRICE);
    }

    /**
     * Get booklist sorted by availability
     *
     * @return sorted booklist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Book> getBooksSortByAvailability() throws DaoMySqlException {
        return bookService.getBooks(BookSort.AVAILABLE);
    }

    /**
     * Get orderlist sortet by status
     *
     * @return sorted orderlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Order> getOrdersSortByCost() throws DaoMySqlException {
        return orderService.getOrders(OrderSort.PRICE);
    }

    /**
     * Get orderlist sorted by status
     *
     * @return sorted orderlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Order> getOrdersSortByStatus() throws DaoMySqlException {
        return orderService.getOrders(OrderSort.STATUS);
    }

    /**
     * Get orderlist sorted by closed date
     *
     * @return sorted orderlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Order> getOrdersSortByClosedDate() throws DaoMySqlException {
        return orderService.getOrders(OrderSort.DATE_CLOSED);
    }

    /**
     * Creating order
     *
     * @param bookId ID of book
     * @param name   customer name
     * @param email  customer email
     * @throws DaoMySqlException    dao error
     * @throws BookServiceException book not available
     */
    @Override
    public void createOrder(int bookId, String name, String email) throws DaoMySqlException, BookServiceException {
        Book book = bookService.getBookById(bookId);
        if (!book.isInStock()) {
            throw new BookServiceException("Book not available!");
        }
        Order newOrder = orderService.createOrder(name, email, book.getPrice());
        int orderId = newOrder.getOrderId();
        orderBookService.createDependency(orderId, bookId);
    }

    /**
     * Cancelling order
     *
     * @param orderID order ID to cancel
     * @throws OrderServiceException order service error
     * @throws DaoMySqlException     dao error
     */
    @Override
    public void cancelOrder(int orderID) throws OrderServiceException, DaoMySqlException {
        orderService.cancelOrder(orderID);
    }

    /**
     * Changing order status newStatus
     *
     * @param orderID   order id
     * @param newStatus new status
     * @throws DaoMySqlException dao error
     */
    @Override
    public void changeOrderStatus(int orderID, OrderStatus newStatus) throws DaoMySqlException {
        orderService.setOrderStatus(orderID, newStatus);
    }


    /**
     * Creates request on book
     *
     * @param bookID book ID
     * @throws DaoMySqlException    dao error
     * @throws BookServiceException book already available
     */
    @Override
    public void createRequest(int bookID) throws DaoMySqlException, BookServiceException {
        Book book = bookService.getBookById(bookID);
        if (book.isInStock()) {
            throw new BookServiceException("Book already available!");
        }
        requestService.createRequest(bookID);
    }

    /**
     * Get book by ID
     *
     * @param bookId id of book
     * @return Book
     * @throws DaoMySqlException dao error
     */
    @Override
    public Book getBookByID(int bookId) throws DaoMySqlException {
        return bookService.getBookById(bookId);
    }

    /**
     * Get sorted requests
     *
     * @param sort type of sort
     * @return requestlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Request> getRequests(RequestSort sort) throws DaoMySqlException {
        return requestService.getRequests(sort, bookService.getBooks());
    }

    /**
     * Gets all requests sorted alphabetically
     *
     * @return requestlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Request> getRequestsSortedAlphabetically() throws DaoMySqlException {
        return getRequests(RequestSort.ALPHABETICAL);
    }

    /**
     * Gets all requests sorted by amount
     *
     * @return requestlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Request> getRequestsSortedAmount() throws DaoMySqlException {
        return getRequests(RequestSort.AMOUNT_REQUESTS);
    }

    /**
     * Get amount of request for concrete book
     *
     * @return amount
     */
    @Override
    public int getAmountRequestsOnBook(int bookID) {
        int result = 0;

        try {
            List<Request> allRequests = requestService.getRequests();
            for (Request request : allRequests) {
                if (request.getBookId() == bookID) {
                    result++;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 0;
        }

        return result;
    }

    /**
     * Set book available
     *
     * @param bookID book ID
     * @throws DaoMySqlException    dao error
     * @throws BookServiceException book already available
     */
    @Override
    public void setBookAvailable(int bookID) throws DaoMySqlException, BookServiceException {
        Book book = bookService.getBookById(bookID);
        if (book.isInStock()) {
            throw new BookServiceException("Book already available");
        }
        if (closeRequestsOnBookAdded) {
            requestService.closeRequestsOnBook(bookID);
        }
        bookService.setBookAvailable(bookID);
    }

    /**
     * Get orderlist of closed order in period
     *
     * @param from from date
     * @param to   to date
     * @return orderlist
     * @throws DaoMySqlException     dao error
     * @throws OrderServiceException order service error
     */
    @Override
    public List<Order> getClosedOrdersByCost(Date from, Date to, OrderSort sort) throws DaoMySqlException, OrderServiceException {
        List<Order> orderList = orderService.getClosedOrders(from, to);
        return orderService.sortOrderList(orderList, sort);
    }

    /**
     * Get income in period
     *
     * @param firstDate  from date
     * @param secondDate to date
     * @return income
     */
    @Override
    public double getPeriodIncome(Date firstDate, Date secondDate) throws DaoMySqlException, OrderServiceException {
        List<Order> orderList = orderService.getClosedOrders(firstDate, secondDate);
        List<OrderBook> orderDependencies = new ArrayList<>();
        for (Order order : orderList) {
            orderDependencies.addAll(orderBookService.getDependenciesByOrderId(order.getOrderId()));
        }

        double totalIncome = 0;

        for (OrderBook orderBook : orderDependencies) {
            totalIncome += bookService.getBookById(orderBook.getBookId()).getPrice();
        }

        return totalIncome;
    }

    /**
     * Get amount of closed orders in period
     *
     * @param firstDate  from date
     * @param secondDate to date
     * @return amount of closed orders
     * @throws DaoMySqlException     dao error
     * @throws OrderServiceException order service error
     */
    @Override
    public int getAmountOfClosedOrders(Date firstDate, Date secondDate) throws DaoMySqlException, OrderServiceException {
        return orderService.getClosedOrders(firstDate, secondDate).size();
    }

    /**
     * Get order by ID
     *
     * @param orderID order ID
     * @throws DaoMySqlException dao error
     */
    @Override
    public Order getOrderByID(int orderID) throws DaoMySqlException {
        return orderService.getOrderById(orderID);
    }

    /**
     * Returns all books in the store
     *
     * @return booklist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Book> getBooks() throws DaoMySqlException {
        return bookService.getBooks();
    }


    /**
     * Get all requests from store
     *
     * @return requestlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Request> getRequests() throws DaoMySqlException {
        return requestService.getRequests();
    }

    /**
     * Gets all orders from store
     *
     * @return orderlist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Order> getOrders() throws DaoMySqlException {
        return orderService.getOrders();
    }

    /**
     * Get all staled books
     *
     * @param sort type of bookSort
     * @return booklist
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Book> getStaleBooks(BookSort sort) throws DaoMySqlException {
        List<Book> bookList = bookService.getBooks(sort);
        Date now = new Date();
        List<Book> staleBooksList = new ArrayList<>();

        for (Book book : bookList) {
            long diff = now.getTime() - book.getIncomeDate().getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diff);
            long daysToStale = monthsToStale * 30;
            if (diffInDays > daysToStale) {
                staleBooksList.add(book);
            }
        }

        return staleBooksList;
    }

    /**
     * Gets all closed orders sorted by price
     *
     * @return sorted orderlist with closed orders
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Order> getClosedOrdersByCost() throws DaoMySqlException {
        List<Order> orders = getOrdersSortByCost();
        List<Order> closedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() != OrderStatus.CLOSED) {
                closedOrders.add(order);
            }
        }
        return closedOrders;
    }

    /**
     * Get order total price
     *
     * @param orderId order id
     * @return total price
     * @throws DaoMySqlException dao error
     */
    @Override
    public double getOrderIncome(int orderId) throws DaoMySqlException {
        double income = 0;
        List<OrderBook> dependencies = orderBookService.getDependenciesByOrderId(orderId);
        for (OrderBook o : dependencies) {
            income += bookService.getBookById(o.getBookId()).getPrice();
        }
        return income;
    }

    /**
     * Get ids of books which order contains
     *
     * @param orderId order id
     * @return list of ids
     * @throws DaoMySqlException dao error
     */
    @Override
    public List<Integer> getBooksIdsInOrder(int orderId) throws DaoMySqlException {
        List<OrderBook> dependencies = orderBookService.getDependenciesByOrderId(orderId);
        List<Integer> bookIds = new ArrayList<>();
        for (OrderBook o : dependencies) {
            bookIds.add(o.getBookId());
        }
        return bookIds;
    }
}
