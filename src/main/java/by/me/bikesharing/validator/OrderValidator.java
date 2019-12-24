package by.me.bikesharing.validator;

import by.me.bikesharing.dao.BikeDao;
import by.me.bikesharing.dao.OrderDao;
import by.me.bikesharing.dao.UserDao;
import by.me.bikesharing.dao.impl.OrderDaoImpl;
import by.me.bikesharing.dao.impl.UserDaoImpl;
import by.me.bikesharing.entity.Order;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.service.OrderService;
import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * The type Order validator.
 */
public class OrderValidator {

    private static final Logger logger = LogManager.getLogger();

    /**
     * The constant TIME_REGEX.
     */
    public static final String TIME_REGEX = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    /**
     * Is parameters null or empty boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     */
    public static boolean isParametersNullOrEmpty(String... parameters) {
        for (String parameter : parameters) {
            if (StringUtils.isNullOrEmpty(parameter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is time valid boolean.
     *
     * @param time the time
     * @return the boolean
     */
    public static boolean isTimeValid(String time) {
        return time.matches(TIME_REGEX);
    }

    /**
     * Is end time after begin time boolean.
     *
     * @param beginTime the begin time
     * @param endTime   the end time
     * @return the boolean
     */
    public static boolean isEndTimeAfterBeginTime(LocalTime beginTime, LocalTime endTime) {
        return endTime.isAfter(beginTime);
    }


    /**
     * Is order finished boolean.
     *
     * @param orderId the order id
     * @return the boolean
     */
    public static boolean isOrderFinished(long orderId) {
        final OrderDao orderDao = OrderDaoImpl.getInstance();
        try {
            Order order = orderDao.findById(orderId);
            return order.getStatus() == 1 && order.getReturnTime() != null;
        } catch (DaoException e) {
            logger.error("Can't validate if order is active. Returning false", e);
        }
        return false;
    }
}
