package by.me.bikesharing.validator;

import by.me.bikesharing.dao.OrganisationDao;
import by.me.bikesharing.dao.impl.OrganisationDaoImpl;
import by.me.bikesharing.entity.Organisation;
import by.me.bikesharing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The type Organisation validator.
 */
public class OrganisationValidator {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Is organisation exists boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isOrganisationExists(long id) {
        Organisation emptyOrganisation = new Organisation();
        final OrganisationDao organisationDao = OrganisationDaoImpl.getInstance();
        try {
            return (!emptyOrganisation.equals(organisationDao.findById(id)));
        } catch (DaoException e) {
            logger.error("Can't validate if organisation exists. Returning false", e);
            return false;
        }
    }
}
