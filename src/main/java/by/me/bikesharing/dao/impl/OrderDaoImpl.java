package by.me.bikesharing.dao.impl;

import by.me.bikesharing.dao.OrderDao;
import by.me.bikesharing.entity.Order;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.pool.CustomConnectionPool;
import by.me.bikesharing.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order dao.
 */
public class OrderDaoImpl implements OrderDao {


    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_ADD_ORDER =
            "INSERT INTO orders(id_user,id_bike,begin_time,end_time,cost,return_time,debt,status,id_card) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String SQL_GET_ORDER_BY_ID =
            "SELECT id,id_user,id_bike,begin_time,end_time,cost,return_time,debt,status,id_card FROM orders WHERE id=?";
    private static final String SQL_GET_ORDER_BY_BIKE_ID =
            "SELECT id,id_user,id_bike,begin_time,end_time,cost,return_time,debt,status,id_card FROM orders WHERE id_bike=?";
    private static final String SQL_GET_USER_ORDERS_BY_LOGIN =
            "SELECT users.login,orders.id,orders.id_user,orders.id_bike,orders.begin_time," +
                    "orders.end_time,orders.cost,orders.return_time,orders.debt,orders.status,orders.id_card from users " +
                    "JOIN orders on users.id = orders.id_user WHERE users.login = ?";
    private static final String SQL_UPDATE_ORDER_BY_ID =
            "UPDATE orders SET id_user=?,id_bike=?,begin_time=?,end_time=?,cost=?,return_time=?,debt=?,status=?,id_card=? WHERE id=?";
    private static String SQL_SET_CARD_BALANCE_BY_SERIAL_NUMBER =
            "UPDATE cards SET balance=? where serial_number=?";
    private static String SQL_GET_ORGANISATION_BALANCE_BY_ID = "SELECT balance from organisations where id=?";
    private static String SQL_SET_ORGANISATION_BALANCE_BY_ID = "UPDATE organisations SET balance =? WHERE id = ?";
    private static final String SQL_GET_CARD_BY_SERIAL_NUMBER =
            "SELECT id,serial_number,balance,id_user FROM cards WHERE serial_number=?";
    private static final String SQL_GET_ORDER_ID_BY_PARAMETERS =
            "SELECT id FROM orders WHERE id_user=? AND id_bike=? " +
                    "AND begin_time=? AND end_time=? AND cost=? AND id_card=?";



    private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();

    private OrderDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OrderDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public long getIdOrderByParameters(long idUser, int idBike, LocalTime beginTime,
                                       LocalTime endTime, BigDecimal cost, long cardId) throws DaoException{
        long id = -1;
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_ID_BY_PARAMETERS);
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idBike);
            preparedStatement.setString(3, beginTime.toString());
            preparedStatement.setString(4, endTime.toString());
            preparedStatement.setBigDecimal(5, cost);
            preparedStatement.setLong(6, cardId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return id;
    }

    @Override
    public boolean payForOrder(String serialNumber, long orgId, BigDecimal cost) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL_GET_CARD_BY_SERIAL_NUMBER);
            preparedStatement.setString(1, serialNumber);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            BigDecimal balance = resultSet.getBigDecimal("balance");
            if (balance.compareTo(cost) < 0) {
                return false;
            }
            preparedStatement = connection.prepareStatement(SQL_SET_CARD_BALANCE_BY_SERIAL_NUMBER);
            preparedStatement.setBigDecimal(1, balance.subtract(cost));
            preparedStatement.setString(2, serialNumber);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(SQL_GET_ORGANISATION_BALANCE_BY_ID);
            preparedStatement.setLong(1, orgId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            BigDecimal orgBalance = resultSet.getBigDecimal("balance");
            preparedStatement = connection.prepareStatement(SQL_SET_ORGANISATION_BALANCE_BY_ID);
            preparedStatement.setBigDecimal(1, orgBalance.add(cost));
            preparedStatement.setLong(2, orgId);
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException e1) {
                logger.error("Can't rollback payForOrder transaction", e);
            }
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                logger.error("Cant' set connection autocommit to true", e);
            }
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public List<Order> getUserOrdersByLogin(String login) throws DaoException {
        List<Order> userOrders = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER_ORDERS_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setIdUser(resultSet.getLong("id_user"));
                order.setIdBike(resultSet.getLong("id_bike"));
                order.setBeginTime(LocalTime.parse(resultSet.getString("begin_time")));
                order.setEndTime(LocalTime.parse(resultSet.getString("end_time")));
                order.setCost(resultSet.getBigDecimal("cost"));
                if (resultSet.getString("return_time") != null) {
                    LocalTime returnTime = LocalTime.parse(resultSet.getString("return_time"));
                    order.setReturnTime(returnTime);
                } else {
                    order.setReturnTime(null);
                }
                order.setDebt(resultSet.getBigDecimal("debt"));
//                if (debt != null) {
//                    order.setDebt(debt);
//                }
//                else{
//                    order.setDebt(null);
//                }
                order.setStatus(resultSet.getInt("status"));
                order.setIdCard(resultSet.getLong("id_card"));
                userOrders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Can't find user orders by login");
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return userOrders;
    }

    @Override
    public List<Order> readAll() throws DaoException {
        return null;
    }

    @Override
    public Order findById(Long id) throws DaoException {
        Order order = new Order();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
                order.setIdUser(resultSet.getLong("id_user"));
                order.setIdBike(resultSet.getLong("id_bike"));
                order.setBeginTime(LocalTime.parse(resultSet.getString("begin_time")));
                order.setEndTime(LocalTime.parse(resultSet.getString("end_time")));
                order.setCost(resultSet.getBigDecimal("cost"));
                if (resultSet.getString("return_time") != null) {
                    LocalTime returnTime = LocalTime.parse(resultSet.getString("return_time"));
                    order.setReturnTime(returnTime);
                } else {
                    order.setReturnTime(null);
                }
                order.setStatus(resultSet.getInt("status"));
                order.setIdCard(resultSet.getLong("id_card"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return order;
    }

    @Override
    public Order findByBikeId(long bikeId) throws DaoException {
        Order order = new Order();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_BY_BIKE_ID);
            preparedStatement.setLong(1, bikeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
                order.setIdUser(resultSet.getLong("id_user"));
                order.setIdBike(resultSet.getLong("id_bike"));
                order.setBeginTime(LocalTime.parse(resultSet.getString("begin_time")));
                order.setEndTime(LocalTime.parse(resultSet.getString("end_time")));
                order.setCost(resultSet.getBigDecimal("cost"));
                order.setStatus(resultSet.getInt("status"));
                order.setIdCard(resultSet.getLong("id_card"));
                if (resultSet.getString("return_time") != null) {
                    LocalTime returnTime = LocalTime.parse(resultSet.getString("return_time"));
                    order.setReturnTime(returnTime);
                } else {
                    order.setReturnTime(null);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't find order by bike id.", e);
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return order;
    }

    @Override
    public boolean create(Order order) throws DaoException {
        ProxyConnection connection;
        PreparedStatement preparedStatement;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_ORDER);
            preparedStatement.setLong(1, order.getIdUser());
            preparedStatement.setLong(2, order.getIdBike());
            preparedStatement.setString(3, order.getBeginTime().toString());
            preparedStatement.setString(4, order.getEndTime().toString());
            preparedStatement.setBigDecimal(5, order.getCost());
            if (order.getReturnTime() != null) {
                preparedStatement.setString(6, order.getReturnTime().toString());
            } else {
                preparedStatement.setString(6, null);
            }

            if (order.getDebt() != null) {
                preparedStatement.setBigDecimal(7, order.getDebt());
            } else {
                preparedStatement.setBigDecimal(7, null);
            }
            preparedStatement.setInt(8, order.getStatus());
            preparedStatement.setLong(9, order.getIdCard());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't create order.", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public Order update(Order order) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_BY_ID);
            preparedStatement.setLong(1, order.getIdUser());
            preparedStatement.setLong(2, order.getIdBike());
            preparedStatement.setString(3, order.getBeginTime().toString());
            preparedStatement.setString(4, order.getEndTime().toString());
            preparedStatement.setBigDecimal(5, order.getCost());
            preparedStatement.setString(6, order.getReturnTime().toString());
            preparedStatement.setBigDecimal(7, order.getDebt());
            preparedStatement.setInt(8, order.getStatus());
            preparedStatement.setLong(9, order.getIdCard());
            preparedStatement.setLong(10, order.getId());

            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_BY_ID);
            preparedStatement.setLong(1, order.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            order.setIdUser(resultSet.getLong("id_user"));
            order.setIdBike(resultSet.getLong("id_bike"));
            order.setBeginTime(LocalTime.parse(resultSet.getString("begin_time")));
            order.setEndTime(LocalTime.parse(resultSet.getString("end_time")));
            order.setCost(resultSet.getBigDecimal("cost"));
            order.setReturnTime(LocalTime.parse(resultSet.getString("return_time")));
            order.setDebt(resultSet.getBigDecimal("debt"));
            order.setStatus(resultSet.getInt("status"));
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    logger.error("Can't update order.", e);
                }
            }
            close(preparedStatement);
            close(connection);
        }
        return order;
    }
}
