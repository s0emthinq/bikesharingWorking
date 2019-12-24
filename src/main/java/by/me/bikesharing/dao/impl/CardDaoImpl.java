package by.me.bikesharing.dao.impl;

import by.me.bikesharing.dao.CardDao;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.Card;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.pool.CustomConnectionPool;
import by.me.bikesharing.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Card dao.
 */
public class CardDaoImpl implements CardDao {

    private static final Logger logger = LogManager.getLogger();
    private static final CardDaoImpl INSTANCE = new CardDaoImpl();

    private static final String SQL_SET_CARD_BALANCE_BY_SERIAL_NUMBER =
            "UPDATE cards set balance=? WHERE serial_number=?";
    private static final String SQL_GET_CARD_BALANCE_BY_SERIAL_NUMBER =
            "SELECT balance FROM cards WHERE serial_number=?";
    private static final String SQL_GET_CARD_BY_SERIAL_NUMBER =
            "SELECT id,serial_number,balance,id_user FROM cards WHERE serial_number=?";
    private static final String SQL_GET_CARD_BY_ID =
            "SELECT id,serial_number,balance,id_user FROM cards WHERE id=?";
    private static final String SQL_GET_USER_CARDS_BY_LOGIN =
            "SELECT cards.id,serial_number,balance,id_user FROM cards " +
                    "JOIN users ON users.id = cards.id_user WHERE login=?";
    private static final String SQL_UPDATE_CARD_BY_ID =
            "UPDATE cards SET serial_number = ?,balance = ?, id_user = ? WHERE id = ?";
    private static final String SQL_ADD_CARD =
            "INSERT INTO cards(serial_number,balance,id_user) VALUES(?,?,?)";

    private CardDaoImpl() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CardDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Card> readAllUserCardByLogin(String login) throws DaoException {
        List<Card> userCards = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER_CARDS_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getLong("id"));
                card.setSerialNumber(resultSet.getString("serial_number"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setIdUser(resultSet.getLong("id_user"));
                userCards.add(card);
            }
            return userCards;
        } catch (SQLException e) {
            logger.error("Can't deposit money on card.");
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean depositMoneyOnCard(String serialNumber, BigDecimal amount) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_CARD_BALANCE_BY_SERIAL_NUMBER);
            preparedStatement.setString(1, serialNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                BigDecimal cardBalance = resultSet.getBigDecimal("balance");
                preparedStatement = connection.prepareStatement(SQL_SET_CARD_BALANCE_BY_SERIAL_NUMBER);
                preparedStatement.setBigDecimal(1, cardBalance.add(amount));
                preparedStatement.setString(2, serialNumber);
            }
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't deposit money on card.");
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public Card findBySerialNumber(String serialNumber) throws DaoException {
        Card card = new Card();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_CARD_BY_SERIAL_NUMBER);
            preparedStatement.setString(1, serialNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card.setId(resultSet.getLong("id"));
                card.setSerialNumber(resultSet.getString("serial_number"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setIdUser(resultSet.getLong("id_user"));
            }
            return card;
        } catch (SQLException e) {
            logger.error("Can't deposit money on card.");
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public List<Card> readAll() throws DaoException {
        return null;
    }

    @Override
    public Card findById(Long id) throws DaoException {
        Card card = new Card();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_CARD_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card.setId(resultSet.getLong("id"));
                card.setSerialNumber(resultSet.getString("serial_number"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                card.setIdUser(resultSet.getLong("id_user"));
            }
            return card;
        } catch (SQLException e) {
            logger.error("Can't deposit money on card.");
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean create(Card card) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_CARD);
            preparedStatement.setString(1, card.getSerialNumber());
            preparedStatement.setBigDecimal(2,card.getBalance());
            preparedStatement.setLong(3,card.getIdUser());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't create card.");
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean delete(Card card) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public Card update(Card card) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_CARD_BY_ID);
            preparedStatement.setString(1, card.getSerialNumber());
            preparedStatement.setBigDecimal(2, card.getBalance());
            preparedStatement.setLong(3, card.getIdUser());
            preparedStatement.setLong(4, card.getId());
            if (preparedStatement.executeUpdate() == 1) {
                return card;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return new Card();
    }
}
