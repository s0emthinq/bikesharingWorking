package by.me.bikesharing.dao;

import by.me.bikesharing.entity.Order;
import by.me.bikesharing.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

/**
 * The interface Order dao.
 */
public interface OrderDao extends BaseDao<Long, Order> {
    /**
     * Find by bike id order.
     *
     * @param bikeId the bike id
     * @return the order
     * @throws DaoException the dao exception
     */
    Order findByBikeId(long bikeId) throws DaoException;

    /**
     * Gets id order by parameters.
     *
     * @param idUser    the id user
     * @param idBike    the id bike
     * @param beginTime the begin time
     * @param endTime   the end time
     * @param cost      the cost
     * @param cardId    the card id
     * @return the id order by parameters
     * @throws DaoException the dao exception
     */
    long getIdOrderByParameters(long idUser, int idBike, LocalTime beginTime,
                                LocalTime endTime, BigDecimal cost, long cardId) throws DaoException;

    /**
     * Gets user orders by login.
     *
     * @param login the login
     * @return the user orders by login
     * @throws DaoException the dao exception
     */
    List<Order> getUserOrdersByLogin(String login) throws DaoException;

    /**
     * Pay for order boolean.
     *
     * @param serialNumber the serial number
     * @param orgId        the org id
     * @param cost         the cost
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean payForOrder(String serialNumber, long orgId, BigDecimal cost) throws DaoException;
}
