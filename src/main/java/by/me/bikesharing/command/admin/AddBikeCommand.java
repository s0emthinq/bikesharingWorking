package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.BikeValidator;
import by.me.bikesharing.validator.OrganisationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The type Add bike command.
 */
public class AddBikeCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private static final BikeService service = new BikeService();

    private static final String PARAM_NAME_LATITUDE = "latitude";
    private static final String PARAM_NAME_LONGITUDE = "longitude";
    private static final String PARAM_NAME_COST_PER_HOUR = "cost_per_hour";
    private static final String PARAM_NAME_STATUS = "status";
    private static final String PARAM_NAME_ID_ORGANISATION = "id_organisation";
    private TextManager textManager;


    @Override
    public Router execute(HttpServletRequest request) {

        request.removeAttribute("message");

        String latitudeAsString = request.getParameter(PARAM_NAME_LATITUDE);
        String longitudeAsString = request.getParameter(PARAM_NAME_LONGITUDE);
        String costPerHourAsString = request.getParameter(PARAM_NAME_COST_PER_HOUR);
        String statusAsString = request.getParameter(PARAM_NAME_STATUS);
        String organisationIdAsString = request.getParameter(PARAM_NAME_ID_ORGANISATION);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);
        String message = textManager.getProperty("message.bikeAdded");

        boolean flag = true;

        while (flag) {

            if (BikeValidator.isParametersNullOrEmpty(latitudeAsString, longitudeAsString,
                    costPerHourAsString, statusAsString, organisationIdAsString)) {
                message = textManager.getProperty("message.parameters.empty");
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

            if (!BikeValidator.isBikeCoordinatesUnique(latitude, longitude)) {
                message = textManager.getProperty("message.parameters.bikeWithSuchCoordinatesExist");
                break;
            }

            Bike bike = new Bike(latitude, longitude, costPerHour, status, organisationId);

            try {
                service.addBike(bike);
            } catch (ServiceException e) {
                logger.error("Can't execute add bike command");
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }

            break;
        }

        request.setAttribute("message", message);
        return CommandEnum.FORWARD_TO_ADD_BIKE.getCommand().execute(request);
    }
}
