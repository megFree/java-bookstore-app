package com.bookstore.services.bookservice;

import com.bookstore.dao.GenericDao;
import com.bookstore.dao.mysql.BookDaoMySql;
import com.bookstore.dao.mysql.DaoMySqlException;
import com.bookstore.models.Book;
import com.bookstore.util.annotations.InjectDao;
import com.bookstore.util.annotations.Singleton;
import com.bookstore.util.enums.BookSort;
import com.bookstore.util.helpers.BookComparator;

import java.util.List;


@Singleton
public class BookService {
    @InjectDao(daoClass = BookDaoMySql.class)
    private GenericDao<Book> dao;

    /**
     * Return sorted bookList
     *
     * @param sort sort type
     * @param arr  booklist
     * @return sorted booklist
     */
    public List<Book> sortBooks(BookSort sort, List<Book> arr) {
        arr.sort(new BookComparator(sort));
        return arr;
    }

    /**
     * Return book object by id from db
     *
     * @param id book id
     * @return book object
     * @throws DaoMySqlException dao error
     */
    public Book getBookById(int id) throws DaoMySqlException {
        return dao.getById(id);
    }

    /**
     * Return all books from db
     *
     * @return booklist
     * @throws DaoMySqlException dao error
     */
    public List<Book> getBooks() throws DaoMySqlException {
        return dao.getAll();
    }

    /**
     * Return books by sort
     *
     * @param sort sort type
     * @return bookList
     * @throws DaoMySqlException dao error
     */
    public List<Book> getBooks(BookSort sort) throws DaoMySqlException {
        return sortBooks(sort, getBooks());
    }

    /**
     * Set book available in store
     *
     * @param bookId book id
     * @throws DaoMySqlException dao error
     */
    public void setBookAvailable(int bookId) throws DaoMySqlException {
        Book book = dao.getById(bookId);
        book.setInStock(true);
        dao.update(book);
    }
}
