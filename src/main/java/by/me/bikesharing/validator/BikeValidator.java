package by.me.bikesharing.validator;

import by.me.bikesharing.dao.impl.BikeDaoImpl;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.service.BikeService;
import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * The type Bike validator.
 */
public class BikeValidator {

    private static final Logger logger = LogManager.getLogger();

    /**
     * The constant ID_REGEX.
     */
    public static final String ID_REGEX = "\\d{1,6}";
    /**
     * The constant COORDINATES_REGEX.
     */
    public static final String COORDINATES_REGEX = "\\d{2}.\\d{2,4}";
    /**
     * The constant COST_PER_HOUR_REGEX.
     */
    public static final String COST_PER_HOUR_REGEX = "\\d{0,2}(\\.\\d{1,2})?";
    /**
     * The constant STATUS_REGEX.
     */
    public static final String STATUS_REGEX = "[0-1]";
    /**
     * The constant MIN_MINSK_LATITUDE.
     */
    public static final double MIN_MINSK_LATITUDE = 53.8501;
    /**
     * The constant MAX_MINSK_LATITUDE.
     */
    public static final double MAX_MINSK_LATITUDE = 53.9501;
    /**
     * The constant MIN_MINSK_LONGITUDE.
     */
    public static final double MIN_MINSK_LONGITUDE = 27.4531;
    /**
     * The constant MAX_MINSK_LONGITUDE.
     */
    public static final double MAX_MINSK_LONGITUDE = 27.6401;

    private static final BikeDaoImpl bikeDao = BikeDaoImpl.getInstance();
    private static final BikeService bikeService = new BikeService();

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
     * Is latitude valid boolean.
     *
     * @param latitude the latitude
     * @return the boolean
     */
    public static boolean isLatitudeValid(String latitude) {
        if (latitude.matches(COORDINATES_REGEX)) {
            return Double.parseDouble(latitude) >= MIN_MINSK_LATITUDE && Double.parseDouble(latitude) <= MAX_MINSK_LATITUDE;
        } else {
            return false;
        }
    }

    /**
     * Is longitude valid boolean.
     *
     * @param longitude the longitude
     * @return the boolean
     */
    public static boolean isLongitudeValid(String longitude) {
        if (longitude.matches(COORDINATES_REGEX)) {
            return Double.parseDouble(longitude) >= MIN_MINSK_LONGITUDE && Double.parseDouble(longitude) <= MAX_MINSK_LONGITUDE;
        } else {
            return false;
        }
    }

    /**
     * Is cost per hour valid boolean.
     *
     * @param costPerHour the cost per hour
     * @return the boolean
     */
    public static boolean isCostPerHourValid(String costPerHour) {
        return costPerHour.matches(COST_PER_HOUR_REGEX);
    }

    /**
     * Is status valid boolean.
     *
     * @param status the status
     * @return the boolean
     */
    public static boolean isStatusValid(String status) {
        return status.matches(STATUS_REGEX);
    }

    /**
     * Is organisation id valid boolean.
     *
     * @param idOrganisation the id organisation
     * @return the boolean
     */
    public static boolean isOrganisationIdValid(String idOrganisation) {
        return idOrganisation.matches(ID_REGEX);
    }

    /**
     * Is bike with such id exist boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isBikeWithSuchIdExist(long id) {

        Bike emptyBike = new Bike();
        try {
            return !emptyBike.equals(bikeDao.findById(id));
        } catch (DaoException e) {
            logger.error("Can't validate if bike is exists");
        }
        return false;
    }

    /**
     * Is bike free boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isBikeFree(long id) {
        try {
            return bikeDao.findById(id).getStatus() == 0;
        } catch (DaoException e) {
            logger.error("Can't validate if bike is free. Returning false");
        }
        return false;
    }

    /**
     * Is bike already has such parameters boolean.
     *
     * @param id             the id
     * @param latitude       the latitude
     * @param longitude      the longitude
     * @param costPerHour    the cost per hour
     * @param status         the status
     * @param idOrganisation the id organisation
     * @return the boolean
     */
    public static boolean isBikeAlreadyHasSuchParameters(long id, double latitude, double longitude, double costPerHour,
                                                         int status, long idOrganisation) {
        Bike bike = new Bike(id, latitude, longitude, costPerHour, status, idOrganisation);
        try {
            Bike foundBike = bikeDao.findById(id);
            return bike.equals(foundBike);
        } catch (DaoException e) {
            logger.error("Can't validate if bike already has such parameters. Returning false");
            return false;
        }
    }

    /**
     * Is bike coordinates unique boolean.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     * @return the boolean
     */
    public static boolean isBikeCoordinatesUnique(double latitude, double longitude) {
        try {
            List<Bike> bikes = bikeService.readAllBikes();
            for (Bike bike : bikes) {
                if (bike.getLatitude() == latitude && bike.getLongitude() == longitude) {
                    return false;
                }
            }
        } catch (ServiceException e) {
            logger.error("Can't validate if bike is free. Returning false");
            return false;
        }
        return true;
    }
}
