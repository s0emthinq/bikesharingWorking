package by.me.bikesharing.service;

import by.me.bikesharing.dao.impl.BikeDaoImpl;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/**
 * The type Bike service.
 */
public class BikeService {

    private static final Logger logger = LogManager.getLogger();
    private BikeDaoImpl bikeDao = BikeDaoImpl.getInstance();

    /**
     * Find bike by id bike.
     *
     * @param id the id
     * @return the bike
     * @throws ServiceException the service exception
     */
    public Bike findBikeById(long id) throws ServiceException {
        try {
            return bikeDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Read all bikes list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Bike> readAllBikes() throws ServiceException {
        try {
            return bikeDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete bike by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean deleteBikeById(long id) throws ServiceException {
        try {
            return bikeDao.delete(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Update bike bike.
     *
     * @param bike the bike
     * @return the bike
     * @throws ServiceException the service exception
     */
    public Bike updateBike(Bike bike) throws ServiceException {
        try {
            return bikeDao.update(bike);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Add bike boolean.
     *
     * @param bike the bike
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean addBike(Bike bike) throws ServiceException {
        try {
            return bikeDao.create(bike);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Update bike with check boolean.
     *
     * @param bike the bike
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean updateBikeWithCheck(Bike bike) throws ServiceException {
        try {
            return bikeDao.updateBikeWithCheck(bike);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Update map.
     *
     * @param fileName the file name
     * @param request  the request
     * @throws ServiceException the service exception
     */
    public void updateMap(String fileName, HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        double latitude = (double) session.getAttribute("userLatitude");
        double longitude = (double) session.getAttribute("userLongitude");
        List<Bike> bikes;
        try {
            bikes = bikeDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        JSONObject mainObject = new JSONObject();
        mainObject.put("type", "FeatureCollection");
        JSONArray features = new JSONArray();

        for (Bike bike : bikes) {
            if (bike.getStatus() == 0) {
                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                feature.put("id", bike.getId());
                JSONObject geometry = new JSONObject();
                JSONArray coordinates = new JSONArray();
                coordinates.add(bike.getLatitude());
                coordinates.add(bike.getLongitude());
                geometry.put("coordinates", coordinates);
                geometry.put("type", "Point");
                feature.put("geometry", geometry);
                JSONObject properties = new JSONObject();
                properties.put("hintContent", String.valueOf(bike.getId()));

                JSONObject options = new JSONObject();
                options.put("preset", "islands#greenBicycleIcon");
                feature.put("properties", properties);
                feature.put("options", options);
                features.add(feature);
            }
        }

        JSONObject feature = new JSONObject();
        feature.put("type", "Feature");
        feature.put("id", 0);
        JSONObject geometry = new JSONObject();
        JSONArray coordinates = new JSONArray();
        coordinates.add(latitude);
        coordinates.add(longitude);
        geometry.put("coordinates", coordinates);
        geometry.put("type", "Point");
        feature.put("geometry", geometry);
        JSONObject properties = new JSONObject();
        properties.put("hintContent", "Me");

        JSONObject options = new JSONObject();
        options.put("preset", "islands#yellowPersonCircleIcon");
        feature.put("properties", properties);
        feature.put("options", options);
        features.add(feature);

        mainObject.put("features", features);
        try {
            Files.write(Paths.get(fileName), mainObject.toJSONString().getBytes());
        } catch (IOException e) {
            logger.error("Cant' read file " + fileName, e);
        }
    }
}

