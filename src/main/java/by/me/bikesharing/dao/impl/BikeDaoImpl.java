package by.me.bikesharing.dao.impl;

import by.me.bikesharing.dao.BikeDao;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.me.bikesharing.pool.CustomConnectionPool;
import by.me.bikesharing.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Bike dao.
 */
public class BikeDaoImpl implements BikeDao {

    private static final Logger logger = LogManager.getLogger();
    private static final BikeDaoImpl INSTANCE = new BikeDaoImpl();

    private static final String SQL_SELECT_ALL_BIKES
            = "SELECT id,latitude,longitude,cost_per_hour,status,id_organisation FROM bikes";
    private static final String SQL_DELETE_BIKE_BY_ID = "DELETE FROM bikes WHERE id = ?";
    private static final String SQL_UPDATE_BIKE_BY_ID =
            "UPDATE bikes SET latitude = ?,longitude = ?,cost_per_hour = ?,status = ?,id_organisation = ? WHERE id = ?";
    private static final String SQL_FIND_BIKE_BY_ID
            = "SELECT id,latitude,longitude,cost_per_hour,status,id_organisation FROM bikes WHERE id = ?";
    private static final String SQL_ADD_BIKE =
            "INSERT INTO bikes(latitude,longitude,cost_per_hour,status,id_organisation) VALUES(?,?,?,?,?)";

    private BikeDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BikeDaoImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Bike> readAll() throws DaoException {
        List<Bike> bikes = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet;
        ProxyConnection connection = null;

        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_ALL_BIKES);
            while (resultSet.next()) {
                Bike bike = new Bike();
                bike.setId(resultSet.getInt("id"));
                bike.setLatitude(resultSet.getDouble("latitude"));
                bike.setLongitude(resultSet.getDouble("longitude"));
                bike.setCostPerHour(resultSet.getDouble("cost_per_hour"));
                bike.setStatus(resultSet.getInt("status"));
                bike.setIdOrganisation(resultSet.getLong("id_organisation"));
                bikes.add(bike);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't show all bikes.", e);
        } finally {
            close(statement);
            close(connection);
        }

        return bikes;
    }

    @Override
    public Bike findById(Long id) throws DaoException {
        Bike bike = new Bike();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_BIKE_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bike.setId(id);
                bike.setLatitude(resultSet.getDouble("latitude"));
                bike.setLongitude(resultSet.getDouble("longitude"));
                bike.setCostPerHour(resultSet.getDouble("cost_per_hour"));
                bike.setStatus(resultSet.getInt("status"));
                bike.setIdOrganisation(resultSet.getInt("id_organisation"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return bike;
    }

    @Override
    public boolean create(Bike bike) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_BIKE);
            preparedStatement.setDouble(1, bike.getLatitude());
            preparedStatement.setDouble(2, bike.getLongitude());
            preparedStatement.setDouble(3, bike.getCostPerHour());
            preparedStatement.setInt(4, bike.getStatus());
            preparedStatement.setLong(5, bike.getIdOrganisation());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean delete(Bike bike) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_BIKE_BY_ID);
            preparedStatement.setLong(1, bike.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_BIKE_BY_ID);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
    }

    @Override
    public Bike update(Bike bike) throws DaoException {
        Bike foundBike = new Bike();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();

            preparedStatement = connection.prepareStatement(SQL_FIND_BIKE_BY_ID);
            preparedStatement.setLong(1, bike.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            foundBike.setId(bike.getId());
            foundBike.setLatitude(resultSet.getDouble("latitude"));
            foundBike.setLongitude(resultSet.getDouble("longitude"));
            foundBike.setCostPerHour(resultSet.getDouble("cost_per_hour"));
            foundBike.setStatus(resultSet.getInt("status"));
            foundBike.setIdOrganisation(resultSet.getInt("id_organisation"));

            if (!bike.equals(foundBike)) {
                preparedStatement = connection.prepareStatement(SQL_UPDATE_BIKE_BY_ID);
                preparedStatement.setDouble(1, bike.getLatitude());
                preparedStatement.setDouble(2, bike.getLongitude());
                preparedStatement.setDouble(3, bike.getCostPerHour());
                preparedStatement.setInt(4, bike.getStatus());
                preparedStatement.setLong(5, bike.getIdOrganisation());
                preparedStatement.setDouble(6, bike.getId());
                preparedStatement.executeUpdate();
                return bike;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return foundBike;
    }

    @Override
    public boolean updateBikeWithCheck(Bike bike) throws DaoException {
        Bike foundBike = new Bike();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_BIKE_BY_ID);
            preparedStatement.setLong(1, bike.getId());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            foundBike.setId(bike.getId());
            foundBike.setLatitude(resultSet.getDouble("latitude"));
            foundBike.setLongitude(resultSet.getDouble("longitude"));
            foundBike.setCostPerHour(resultSet.getDouble("cost_per_hour"));
            foundBike.setStatus(resultSet.getInt("status"));
            foundBike.setIdOrganisation(resultSet.getInt("id_organisation"));

            if (!bike.equals(foundBike)) {
                preparedStatement = connection.prepareStatement(SQL_UPDATE_BIKE_BY_ID);
                preparedStatement.setDouble(1, bike.getLatitude());
                preparedStatement.setDouble(2, bike.getLongitude());
                preparedStatement.setDouble(3, bike.getCostPerHour());
                preparedStatement.setInt(4, bike.getStatus());
                preparedStatement.setLong(5, bike.getIdOrganisation());
                preparedStatement.setDouble(6, bike.getId());
                preparedStatement.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection); //вернуть в пул соединений(в самом методе)
        }
        return false;
    }

}
