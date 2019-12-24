package by.me.bikesharing.validator;

import by.me.bikesharing.dao.CardDao;
import by.me.bikesharing.dao.impl.CardDaoImpl;
import by.me.bikesharing.entity.Card;
import by.me.bikesharing.exception.DaoException;
import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Card validator.
 */
public class CardValidator {
    private static final Logger logger = LogManager.getLogger();

    /**
     * The constant SERIAL_NUMBER_REGEX.
     */
    public static final String SERIAL_NUMBER_REGEX = "\\d{16}";
    /**
     * The constant AMOUNT_REGEX.
     */
    public static final String AMOUNT_REGEX = "\\d{0,3}(\\.\\d{1,2})?";

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
     * Is serial number valid boolean.
     *
     * @param serialNumber the serial number
     * @return the boolean
     */
    public static boolean isSerialNumberValid(String serialNumber) {
        return serialNumber.matches(SERIAL_NUMBER_REGEX);
    }

    /**
     * Is amount valid boolean.
     *
     * @param amount the amount
     * @return the boolean
     */
    public static boolean isAmountValid(String amount) {
        return amount.matches(AMOUNT_REGEX);
    }

    /**
     * Is card exist boolean.
     *
     * @param serialNumber the serial number
     * @return the boolean
     */
    public static boolean isCardExist(String serialNumber) {
        final CardDao cardDao = CardDaoImpl.getInstance();
        Card emptyCard = new Card();
        try {
            Card foundCard = cardDao.findBySerialNumber(serialNumber);
            return (!emptyCard.equals(foundCard));
        } catch (DaoException e) {
            logger.info("Can't validate if card exists. Returning false", e);
            return false;
        }
    }
}
