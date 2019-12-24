package by.me.bikesharing.dao;

import by.me.bikesharing.entity.Card;
import by.me.bikesharing.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Card dao.
 */
public interface CardDao extends BaseDao<Long, Card> {
    /**
     * Deposit money on card boolean.
     *
     * @param serialNumber the serial number
     * @param amount       the amount
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean depositMoneyOnCard(String serialNumber, BigDecimal amount) throws DaoException;

    /**
     * Find by serial number card.
     *
     * @param serialNumber the serial number
     * @return the card
     * @throws DaoException the dao exception
     */
    Card findBySerialNumber(String serialNumber) throws DaoException;

    /**
     * Read all user card by login list.
     *
     * @param login the login
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Card> readAllUserCardByLogin(String login) throws DaoException;
}
