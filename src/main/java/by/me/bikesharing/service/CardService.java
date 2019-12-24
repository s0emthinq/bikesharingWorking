package by.me.bikesharing.service;

import by.me.bikesharing.dao.CardDao;
import by.me.bikesharing.dao.UserDao;
import by.me.bikesharing.dao.impl.CardDaoImpl;
import by.me.bikesharing.dao.impl.UserDaoImpl;
import by.me.bikesharing.entity.Card;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * The type Card service.
 */
public class CardService {

    private CardDao cardDao = CardDaoImpl.getInstance();
    private UserDao userDao = UserDaoImpl.getInstance();

    /**
     * Deposit money on card boolean.
     *
     * @param serialNumber the serial number
     * @param amount       the amount
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean depositMoneyOnCard(String serialNumber, BigDecimal amount) throws ServiceException {
        try {
            return cardDao.depositMoneyOnCard(serialNumber, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets all user cards by login.
     *
     * @param login the login
     * @return the all user cards by login
     * @throws ServiceException the service exception
     */
    public List<Card> getAllUserCardsByLogin(String login) throws ServiceException {
        try {
            return cardDao.readAllUserCardByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets card id by serial number.
     *
     * @param serialNumber the serial number
     * @return the card id by serial number
     * @throws ServiceException the service exception
     */
    public long getCardIdBySerialNumber(String serialNumber) throws ServiceException {
        try {
            return cardDao.findBySerialNumber(serialNumber).getId();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets serial number by id card.
     *
     * @param idCard the id card
     * @return the serial number by id card
     * @throws ServiceException the service exception
     */
    public String getSerialNumberByIdCard(long idCard) throws ServiceException {
        try {
            return cardDao.findById(idCard).getSerialNumber();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Link user card boolean.
     *
     * @param login        the login
     * @param serialNumber the serial number
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean linkUserCard(String login, String serialNumber) throws ServiceException {
        try {
            double minBalance = 2;
            double maxBalance = 100;
            Random random = new Random();
            double actualBalance = minBalance + (maxBalance - minBalance) * random.nextDouble();
            long idUser = userDao.findUserByLogin(login).getId();
            Card card = new Card(serialNumber, new BigDecimal(actualBalance), idUser);
            cardDao.create(card);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }



}
