package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.Order;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.service.CardService;
import by.me.bikesharing.service.OrderService;
import by.me.bikesharing.service.UserService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.BikeValidator;
import by.me.bikesharing.validator.CardValidator;
import by.me.bikesharing.validator.OrderValidator;
import by.me.bikesharing.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Order bike command.
 */
public class OrderBikeCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService = new OrderService();
    private UserService userService = new UserService();
    private BikeService bikeService = new BikeService();
    private CardService cardService = new CardService();
    private TextManager textManager;

    private static final String PARAM_NAME_ID = "id";
    private static final String PARAM_NAME_END_TIME = "end_time";
    private static final String ATTR_NAME_CURRENT_USER = "currentUser";
    private static final String PARAM_NAME_SERIAL_NUMBER = "serial_number";

    private static final String TIME_FORMAT = "HH:mm";
    private static final double DELTA_DISTANCE_USER_BIKE = 0.0010;


    @Override
    public Router execute(HttpServletRequest request) {
        request.removeAttribute("message");
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);

        String endTimeAsString = request.getParameter(PARAM_NAME_END_TIME);
        String idAsString = request.getParameter(PARAM_NAME_ID);
        String serialNumber = request.getParameter(PARAM_NAME_SERIAL_NUMBER);

        String message = textManager.getProperty("message.bike.ordered");

        boolean flag = true;

        while (flag) {

            if (OrderValidator.isParametersNullOrEmpty(idAsString, endTimeAsString,serialNumber)) {
                message = textManager.getProperty("message.parameters.empty");
                break;
            }

            if (!CardValidator.isSerialNumberValid(serialNumber) || !CardValidator.isCardExist(serialNumber)) {
                message = textManager.getProperty("message.parameters.invalidSerialNumber");
                break;
            }

            if (!BikeValidator.isIdValid(idAsString)) {
                message = textManager.getProperty("message.parameters.incorrectIdFormat");
                break;
            }

            if (!OrderValidator.isTimeValid(endTimeAsString)) {
                message = textManager.getProperty("message.parameters.wrongEndTimeFormat");
                break;
            }

            LocalTime beginTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            String login = String.valueOf(session.getAttribute(ATTR_NAME_CURRENT_USER));

            int idBike = Integer.parseInt(request.getParameter(PARAM_NAME_ID));
            LocalTime endTime = LocalTime.parse(endTimeAsString);

            if (!BikeValidator.isBikeWithSuchIdExist(idBike)) {
                message = textManager.getProperty("message.parameters.noBikeWithSuchId");
                break;
            }

            if (!OrderValidator.isEndTimeAfterBeginTime(beginTime, endTime)) {
                message = textManager.getProperty("message.parameters.wrongEndTimeValue");
                break;
            }

            BigDecimal cost = null;

            try {
                cost = orderService.calculateOrderCost(idBike, beginTime, endTime);
            } catch (ServiceException e) {
                logger.error("Can' calculate order cost", e);
            }

            if (!orderService.isEnoughMoneyOnCard(serialNumber, cost)) {
                message = textManager.getProperty("message.notEnoughMoney");
                break;
            }

            if (!BikeValidator.isBikeFree(idBike)) {
                message = textManager.getProperty("message.bikeIsTaken");
                break;
            }

            if (!UserValidator.isUserActive(login)) {
                message = textManager.getProperty("message.youAreBlocked");
                break;
            }



            try {
                User currentUser = userService.findUserByLogin(login);
                long cardId = cardService.getCardIdBySerialNumber(serialNumber);
                Order order = new Order(currentUser.getId(), idBike, beginTime,
                        endTime, cost, null, null, 0, cardId);
                orderService.addOrder(order);

                Bike bike = bikeService.findBikeById(idBike);
                long idOrder = orderService.getIdOrderByParameters(currentUser.getId(), idBike, beginTime,
                        endTime, cost, cardId);
                orderService.payForOrder(idOrder, serialNumber);
                session.setAttribute("userLatitude", bike.getLatitude() + DELTA_DISTANCE_USER_BIKE);
                session.setAttribute("userLongitude", bike.getLongitude() + DELTA_DISTANCE_USER_BIKE);
            } catch (ServiceException e) {
                logger.error("Can't findUserByLogin or create order.");
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }
            break;
        }
        request.setAttribute("message", message);
        return CommandEnum.FORWARD_TO_ORDER_BIKE.getCommand().execute(request);
    }
}