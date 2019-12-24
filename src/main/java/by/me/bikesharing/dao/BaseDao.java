package by.me.bikesharing.dao;

import by.me.bikesharing.entity.Entity;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.pool.CustomConnectionPool;
import by.me.bikesharing.pool.ProxyConnection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * The interface Base dao.
 *
 * @param <K> the type parameter
 * @param <T> the type parameter
 */
public interface BaseDao<K, T extends Entity> {

    /**
     * Read all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> readAll() throws DaoException;

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     * @throws DaoException the dao exception
     */
    T findById(K id) throws DaoException;

    /**
     * Create boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean create(T t) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(T t) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(K id) throws DaoException;

    /**
     * Update t.
     *
     * @param t the t
     * @return the t
     * @throws DaoException the dao exception
     */
    T update(T t) throws DaoException;

    /**
     * Close.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // fixme throw new DaoException(e)?
            }
        }
    }

    /**
     * Close.
     *
     * @param connection the connection
     */
    default void close(ProxyConnection connection) {
        if (connection != null) {
            CustomConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
