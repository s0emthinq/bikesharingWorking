package by.me.bikesharing.service;

import by.me.bikesharing.dao.BikeDao;
import by.me.bikesharing.dao.CardDao;
import by.me.bikesharing.dao.OrderDao;
import by.me.bikesharing.dao.UserDao;
import by.me.bikesharing.dao.impl.BikeDaoImpl;
import by.me.bikesharing.dao.impl.CardDaoImpl;
import by.me.bikesharing.dao.impl.OrderDaoImpl;
import by.me.bikesharing.dao.impl.UserDaoImpl;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.Card;
import by.me.bikesharing.entity.Order;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The type Order service.
 */
public class OrderService {

    private final double MINUTES_IN_HOUR = 60;

    private static final Logger logger = LogManager.getLogger();

    private BikeDao bikeDao = BikeDaoImpl.getInstance();
    private OrderDao orderDao = OrderDaoImpl.getInstance();
    private CardDao cardDao = CardDaoImpl.getInstance();

    /**
     * Calculate order cost big decimal.
     *
     * @param id        the id
     * @param beginTime the begin time
     * @param endTime   the end time
     * @return the big decimal
     * @throws ServiceException the service exception
     */
    public BigDecimal calculateOrderCost(long id, LocalTime beginTime, LocalTime endTime) throws ServiceException {
        double cost;
        try {
            cost = bikeDao.findById(id).getCostPerHour();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return BigDecimal.valueOf(Duration.between(beginTime, endTime).toMinutes() / MINUTES_IN_HOUR * cost);
    }

    /**
     * Gets user orders by login.
     *
     * @param login the login
     * @return the user orders by login
     * @throws ServiceException the service exception
     */
    public List<Order> getUserOrdersByLogin(String login) throws ServiceException {
        try {
            return orderDao.getUserOrdersByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Add order boolean.
     *
     * @param order the order
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean addOrder(Order order) throws ServiceException {
        try {
            Bike bike = bikeDao.findById(order.getIdBike());
            bike.setStatus(1);
            bikeDao.update(bike);
            return orderDao.create(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Fin order by id order.
     *
     * @param orderId the order id
     * @return the order
     * @throws ServiceException the service exception
     */
    public Order finOrderById(long orderId) throws ServiceException {
        try {
            return orderDao.findById(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Fin order by bike id order.
     *
     * @param bikeId the bike id
     * @return the order
     * @throws ServiceException the service exception
     */
    public Order finOrderByBikeId(long bikeId) throws ServiceException {
        try {
            return orderDao.findByBikeId(bikeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Finish order boolean.
     *
     * @param orderId    the order id
     * @param returnTime the return time
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean finishOrder(long orderId, LocalTime returnTime) throws ServiceException {
        try {
            Order order = orderDao.findById(orderId);
            LocalTime endTime = order.getEndTime();
            Bike bike = bikeDao.findById(order.getIdBike());

            double newCoordinates[] = UserService.generateRandomCoordinatesWithinMinsk();
            bike.setLatitude(newCoordinates[0]);
            bike.setLongitude(newCoordinates[1]);

            if (endTime.compareTo(returnTime) >= 0) {
                order.setReturnTime(returnTime);
            } else {
                order.setReturnTime(returnTime);
                BigDecimal debt = BigDecimal.valueOf(Duration.between(endTime, returnTime).toMinutes()
                        / MINUTES_IN_HOUR * bike.getCostPerHour());
                order.setDebt(debt);
            }
            order.setStatus(1);
            orderDao.update(order);
            bike.setStatus(0);
            bikeDao.update(bike);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Pay for order boolean.
     *
     * @param orderId      the order id
     * @param serialNumber the serial number
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean payForOrder(long orderId, String serialNumber) throws ServiceException {
        try {
            Order order = orderDao.findById(orderId);
            Bike bike = bikeDao.findById(order.getIdBike());
            return orderDao.payForOrder(serialNumber, bike.getIdOrganisation(), order.getCost());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Is enough money on card boolean.
     *
     * @param serialNumber the serial number
     * @param orderCost    the order cost
     * @return the boolean
     */
    public boolean isEnoughMoneyOnCard(String serialNumber, BigDecimal orderCost) {
        Card card;
        BigDecimal balance;
        try {
            card = cardDao.findBySerialNumber(serialNumber);
            balance = card.getBalance();
            return balance.compareTo(orderCost) >= 0;
        } catch (DaoException e) {
            logger.error("Can't check if user has enough money. Returning false", e);
            return false;
        }
    }

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
     * @throws ServiceException the service exception
     */
    public long getIdOrderByParameters(long idUser, int idBike, LocalTime beginTime, LocalTime endTime,
                                       BigDecimal cost, long cardId) throws ServiceException {
        try {
            return orderDao.getIdOrderByParameters(idUser, idBike, beginTime,
                    endTime, cost, cardId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

