package by.me.bikesharing.dao;

import by.me.bikesharing.entity.Order;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao extends BaseDao<Long, User> {
    /**
     * Find user by login user.
     *
     * @param login the login
     * @return the user
     * @throws DaoException the dao exception
     */
    User findUserByLogin(String login) throws DaoException;

    /**
     * Gets user balance by login.
     *
     * @param login the login
     * @return the user balance by login
     * @throws DaoException the dao exception
     */
    BigDecimal getUserBalanceByLogin(String login) throws DaoException;

    /**
     * Update user balance by login boolean.
     *
     * @param login      the login
     * @param newBalance the new balance
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserBalanceByLogin(String login, BigDecimal newBalance) throws DaoException;

    /**
     * Update user status by id boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserStatusById(long id, int status) throws DaoException;
}
