package by.me.bikesharing.dao.impl;

import by.me.bikesharing.dao.OrganisationDao;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.Organisation;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.pool.CustomConnectionPool;
import by.me.bikesharing.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Organisation dao.
 */
public class OrganisationDaoImpl implements OrganisationDao {

    private static final String SQL_FIND_ORGANISATION_BY_ID = "SELECT id,name,balance from organisations WHERE id=?";

    private static final OrganisationDaoImpl INSTANCE = new OrganisationDaoImpl();

    private OrganisationDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OrganisationDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Organisation> readAll() throws DaoException {
        return null;
    }

    @Override
    public Organisation findById(Long id) throws DaoException {
        Organisation organisation = new Organisation();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = CustomConnectionPool.INSTANCE.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ORGANISATION_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                organisation.setId(resultSet.getLong("id"));
                organisation.setName(resultSet.getString("name"));
                organisation.setBalance(resultSet.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return organisation;
    }

    @Override
    public boolean create(Organisation organisation) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Organisation organisation) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public Organisation update(Organisation organisation) throws DaoException {
        return null;
    }
}
