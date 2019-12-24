package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.BikeValidator;
import by.me.bikesharing.validator.OrganisationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The type Update bike command.
 */
public class UpdateBikeCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private static final BikeService service = new BikeService();

    private static final String PARAM_NAME_ID = "id";
    private static final String PARAM_NAME_LATITUDE = "new_latitude";
    private static final String PARAM_NAME_LONGITUDE = "new_longitude";
    private static final String PARAM_NAME_COST_PER_HOUR = "new_cost_per_hour";
    private static final String PARAM_NAME_STATUS = "new_status";
    private static final String PARAM_NAME_ID_ORGANISATION = "new_id_organisation";
    private TextManager textManager;


    @Override
    public Router execute(HttpServletRequest request) {

        request.removeAttribute("message");
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);

        String message = textManager.getProperty("message.bikeSuccessfullyUpdated");

        String idAsString = request.getParameter(PARAM_NAME_ID);
        String latitudeAsString = request.getParameter(PARAM_NAME_LATITUDE);
        String longitudeAsString = request.getParameter(PARAM_NAME_LONGITUDE);
        String costPerHourAsString = request.getParameter(PARAM_NAME_COST_PER_HOUR);
        String statusAsString = request.getParameter(PARAM_NAME_STATUS);
        String organisationIdAsString = request.getParameter(PARAM_NAME_ID_ORGANISATION);

        boolean flag = true;

        while (flag) {

            if (BikeValidator.isParametersNullOrEmpty(idAsString, latitudeAsString, longitudeAsString,
                    costPerHourAsString, statusAsString, organisationIdAsString)) {
                message = textManager.getProperty("message.parameters.empty");
                break;
            }

            if (!BikeValidator.isIdValid(idAsString)) {
                message = textManager.getProperty("message.parameters.incorrectIdFormat");
                break;
            }

            long id = Long.parseLong(request.getParameter(PARAM_NAME_ID));

            if (!BikeValidator.isBikeWithSuchIdExist(id)) {
                message = textManager.getProperty("message.parameters.noBikeWithSuchId");
                break;
            }

            if (!BikeValidator.isLatitudeValid(latitudeAsString)) {
                message = textManager.getProperty("message.parameters.incorrectLatitude");
                break;
            }

            if (!BikeValidator.isLongitudeValid(longitudeAsString)) {
                message = textManager.getProperty("message.parameters.incorrectLongitude");
                break;
            }

            if (!BikeValidator.isCostPerHourValid(costPerHourAsString)) {
                message = textManager.getProperty("message.incorrectCostPerHour");
                break;
            }

            if (!BikeValidator.isStatusValid(statusAsString)) {
                message = textManager.getProperty("message.incorrectStatus");
                break;
            }

            if (!BikeValidator.isOrganisationIdValid(organisationIdAsString)) {
                message = textManager.getProperty("message.incorrectOrganisationId");
                break;
            }

            if (!OrganisationValidator.isOrganisationExists(Integer.valueOf(organisationIdAsString))) {
                message = textManager.getProperty("message.NoSuchOrganisation");
                break;
            }


            double latitude = Double.parseDouble(request.getParameter(PARAM_NAME_LATITUDE));
            double longitude = Double.parseDouble(request.getParameter(PARAM_NAME_LONGITUDE));
            double costPerHour = Double.parseDouble(request.getParameter(PARAM_NAME_COST_PER_HOUR));
            int status = Integer.parseInt(request.getParameter(PARAM_NAME_STATUS));
            long organisationId = Long.parseLong(request.getParameter(PARAM_NAME_ID_ORGANISATION));

            if (BikeValidator.isBikeAlreadyHasSuchParameters(id, latitude, longitude, costPerHour,
                    status, organisationId)) {
                message = textManager.getProperty("message.tryingToAssignSameParametersToBike");
                break;
            }

            Bike bike = new Bike(id, latitude, longitude, costPerHour, status, organisationId);

            try {
                service.updateBike(bike);
            } catch (ServiceException e) {
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }

            break;
        }

        request.setAttribute("message", message);
        return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.updateBike"));
    }
}

