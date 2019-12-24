package by.me.bikesharing.dao;


import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.exception.DaoException;

import java.math.BigDecimal;

/**
 * The interface Bike dao.
 */
public interface BikeDao extends BaseDao<Long, Bike> {
    /**
     * Update bike with check boolean.
     *
     * @param bike the bike
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateBikeWithCheck(Bike bike) throws DaoException;
}
