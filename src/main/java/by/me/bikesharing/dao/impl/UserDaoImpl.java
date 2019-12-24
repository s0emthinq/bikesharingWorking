package by.me.bikesharing.dao.impl;

import by.me.bikesharing.dao.UserDao;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.pool.CustomConnectionPool;
import by.me.bikesharing.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User dao.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_READ_ALL_USERS = "SELECT id,login,password,email,role,status,image FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id,login,password,email,role,status,image FROM users where id=?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id,login,password,email,role,status,image FROM users where login=?";
    private static final String SQL_GET_USER_BALANCE_BY_LOGIN =
            "SELECT cards.balance FROM cards JOIN users ON users.id=cards.id_user where users.login=?";
    private static String SQL_SET_USER_BALANCE_BY_LOGIN =
            "UPDATE cards SET balance=? where user_id = (SELECT id FROM users where login = ?)"; // fixme
    private static String SQL_SET_USER_STATUS_BY_ID =
            "UPDATE users SET status=? where id=?";
    private static final String SQL_ADD_USER =
            "INSERT INTO users(login,password,email,role,status,image) VALUES(?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER_BY_ID =
            "UPDATE users SET login = ?,password = ?,email = ?,role = ?,status = ?,image = ? WHERE id=?";

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean updateUserBalanceByLogin(String login, BigDecimal newBalance) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_USER_BALANCE_BY_LOGIN);
            preparedStatement.setBigDecimal(1, newBalance);
            preparedStatement.setString(2, login);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean updateUserStatusById(long id, int status) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_USER_STATUS_BY_ID);
            preparedStatement.setInt(1, status);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }


    @Override
    public BigDecimal getUserBalanceByLogin(String login) throws DaoException {
        BigDecimal balance;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER_BALANCE_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getBigDecimal("balance");
            } else {
                return new BigDecimal(-1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return balance;
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getInt("role"));
                user.setStatus(resultSet.getInt("status"));
                user.setImage(resultSet.getString("image"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return user;
    }

    @Override
    public List<User> readAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_READ_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getInt("role"));
                user.setStatus(resultSet.getInt("status"));
                user.setImage(resultSet.getString("image"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't show all users.", e);
        } finally {
            close(statement);
            close(connection);
        }
        return users;
    }

    @Override
    public User findById(Long id) throws DaoException {
        User user = new User();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getInt("role"));
                user.setStatus(resultSet.getInt("status"));
                user.setImage(resultSet.getString("image"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRole());
            preparedStatement.setInt(5, user.getStatus());
            preparedStatement.setString(6, user.getImage());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean delete(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(User user) throws DaoException {
        User foundUser = new User();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
            preparedStatement.setLong(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            foundUser.setId(user.getId());
            foundUser.setLogin(resultSet.getString("login"));
            foundUser.setPassword(resultSet.getString("password"));
            foundUser.setEmail(resultSet.getString("email"));
            foundUser.setRole(resultSet.getInt("role"));
            foundUser.setStatus(resultSet.getInt("status"));
            foundUser.setImage(resultSet.getString("image"));
            if (!user.equals(foundUser)) {
                preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setInt(4, user.getRole());
                preparedStatement.setInt(5, user.getStatus());
                preparedStatement.setString(6, user.getImage());
                preparedStatement.setLong(7, user.getId());
                preparedStatement.executeUpdate();
                return user;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return user;
    }
}
