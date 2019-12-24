package by.me.bikesharing.validator;

import by.me.bikesharing.dao.BikeDao;
import by.me.bikesharing.dao.UserDao;
import by.me.bikesharing.dao.impl.BikeDaoImpl;
import by.me.bikesharing.dao.impl.UserDaoImpl;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.exception.ServiceException;
import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type User validator.
 */
public class UserValidator {

    private static final Logger logger = LogManager.getLogger();
    private static UserDao userDao = UserDaoImpl.getInstance();

    /**
     * The constant ID_REGEX.
     */
    public static final String ID_REGEX = "\\d{1,6}";
    /**
     * The constant LOGIN_REGEX.
     */
    public static final String LOGIN_REGEX = "^(?=.{4,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    /**
     * The constant PASSWORD_REGEX.
     */
    public static final String PASSWORD_REGEX = "^(?=.{4,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    /**
     * The constant EMAIL_REGEX.
     */
    public static final String EMAIL_REGEX = ".+\\@.+\\..+";

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
     * Is id valid boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isIdValid(String id) {
        return id.matches(ID_REGEX);
    }

    /**
     * Is login valid boolean.
     *
     * @param login the login
     * @return the boolean
     */
    public static boolean isLoginValid(String login) {
        return login.matches(LOGIN_REGEX);
    }

    /**
     * Is password valid boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * Is user exists boolean.
     *
     * @param login the login
     * @return the boolean
     */
    public static boolean isUserExists(String login) {
        User emptyUser = new User();
        try {
            return !emptyUser.equals(userDao.findUserByLogin(login));
        } catch (DaoException e) {
            logger.error(e);
            return false;
        }
    }

    /**
     * Is user admin boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isUserAdmin(long id) {
        try {
            return userDao.findById(id).getRole() == 1;
        } catch (DaoException e) {
            logger.error("Can't validate if user admin. Returning true(he is admin)", e);
            return true;
        }
    }

    /**
     * Is user exists boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isUserExists(long id) {
        User emptyUser = new User();
        try {
            return !emptyUser.equals(userDao.findById(id));
        } catch (DaoException e) {
            logger.error(e);
            return false;
        }
    }

    /**
     * Is passwords same boolean.
     *
     * @param password         the password
     * @param repeatedPassword the repeated password
     * @return the boolean
     */
    public static boolean isPasswordsSame(String password, String repeatedPassword) {
        return password.matches(repeatedPassword);
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }

    /**
     * Is user active boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isUserActive(long id) {
        try {
            return userDao.findById(id).getStatus() == 0;
        } catch (DaoException e) {
            logger.error("Can't validate if user is active. Returning false", e);
            return false;
        }
    }

    /**
     * Is user active boolean.
     *
     * @param login the login
     * @return the boolean
     */
    public static boolean isUserActive(String login) {
        try {
            return userDao.findUserByLogin(login).getStatus() == 0;
        } catch (DaoException e) {
            logger.error("Can't validate if user is active. Returning false", e);
            return false;
        }
    }

}
