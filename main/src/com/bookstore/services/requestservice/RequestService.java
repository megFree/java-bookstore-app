package com.bookstore.services.requestservice;

import com.bookstore.dao.GenericDao;
import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.dao.mysql.RequestDaoMySql;
import com.bookstore.models.Book;
import com.bookstore.models.Request;
import com.bookstore.util.annotations.InjectDao;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.RequestSort;
import com.bookstore.util.helpers.RequestComparator;

import java.util.Date;
import java.util.List;

@Singleton
public class RequestService {
    @InjectDao(daoClass = RequestDaoMySql.class)
    private GenericDao<Request> dao;

    /**
     * Pushes new request to database
     *
     * @param bookId requested book id
     */
    public void createRequest(int bookId) throws DaoMySqlException {
        dao.create(new Request(bookId, new Date()));
    }

    /**
     * Close all requests on concrete book
     *
     * @param bookId book id
     * @throws DaoMySqlException dao error
     */
    public void closeRequestsOnBook(int bookId) throws DaoMySqlException {
        List<Request> requests = dao.getAll();
        for (Request request : requests) {
            if (request.getBookId() == bookId && !request.isClosed()) {
                request.setClosed(true);
                dao.update(request);
            }
        }
    }

    /**
     * Get all requests
     *
     * @return requests list
     * @throws DaoMySqlException dao error
     */
    public List<Request> getRequests() throws DaoMySqlException {
        return dao.getAll();
    }

    /**
     * Get all requests sorted
     *
     * @param sort  sort type
     * @param books book list
     * @return request list
     * @throws DaoMySqlException dao error
     */
    public List<Request> getRequests(RequestSort sort, List<Book> books) throws DaoMySqlException {
        List<Request> requests = dao.getAll();
        RequestComparator comparator = new RequestComparator(sort, requests, books);
        requests.sort(comparator);
        return requests;
    }
}
