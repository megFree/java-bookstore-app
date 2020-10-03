package com.bookstore.dao.mysql;

import com.bookstore.dao.ConnectionManager;
import com.bookstore.dao.GenericDao;
import com.bookstore.models.Identified;
import com.bookstore.util.annotations.InjectObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public abstract class AbstractDaoMySql<T extends Identified> implements GenericDao<T> {
    @InjectObject
    private ConnectionManager connectionManager;

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Get create query
     *
     * @return sql query
     */
    public abstract String getCreateQuery();

    /**
     * Prepares statement for create row
     *
     * @param statement statement
     * @param obj       object to
     * @throws DaoMySqlException dao error
     */
    public abstract void prepareStatementForCreate(PreparedStatement statement, T obj) throws DaoMySqlException;

    /**
     * Prepares statement for update row
     *
     * @param statement statement
     * @param obj       object to
     * @throws DaoMySqlException dap error
     */
    public abstract void prepareStatementForUpdate(PreparedStatement statement, T obj) throws DaoMySqlException;

    /**
     * Get select sql query
     *
     * @return sql query
     */
    public abstract String getSelectQuery();

    /**
     * Parsing result set and returns list of concrete objects
     *
     * @param rs resultset
     * @return object list
     * @throws DaoMySqlException dao error
     */
    public abstract List<T> parseResultSet(ResultSet rs) throws DaoMySqlException;

    /**
     * Get update sql query
     *
     * @return sql query
     */
    public abstract String getUpdateQuery();

    /**
     * Pushes new item to database
     *
     * @param obj object
     * @return return added object
     * @throws DaoMySqlException modifying error
     */
    public T create(T obj) throws DaoMySqlException {
        String sql = getCreateQuery();
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            prepareStatementForCreate(statement, obj);

            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DaoMySqlException("On creating modify more than 1 rows");
            }
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }

        List<T> parsedSet;
        sql = getSelectQuery() + " WHERE id = last_insert_id()";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            parsedSet = parseResultSet(rs);
            if (parsedSet == null || parsedSet.size() != 1) {
                throw new DaoMySqlException("Cannot return last created element");
            }
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
        return parsedSet.iterator().next();
    }

    /**
     * Get instance of model from db by id
     *
     * @param id od
     * @return instance
     * @throws DaoMySqlException dao error
     */
    @Override
    public T getById(int id) throws DaoMySqlException {
        String sql = getSelectQuery() + " WHERE id = ?";
        List<T> parsedList;
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            parsedList = parseResultSet(rs);
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
        return parsedList.iterator().next();
    }

    /**
     * Get list of instances of all models from db
     *
     * @return object list
     */
    @Override
    public List<T> getAll() throws DaoMySqlException {
        String sql = getSelectQuery();
        List<T> parsedList;
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            parsedList = parseResultSet(rs);
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }
        return parsedList;
    }

    @Override
    public T update(T object) throws DaoMySqlException {
        String sql = getUpdateQuery();
        int id = object.getOrderId();
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoMySqlException(e);
        }

        return getById(id);
    }
}
