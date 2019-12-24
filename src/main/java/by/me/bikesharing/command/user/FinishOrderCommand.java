package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.Order;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.service.CardService;
import by.me.bikesharing.service.OrderService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Finish order command.
 */
public class FinishOrderCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private static final String TIME_FORMAT = "HH:mm";
    private static final String PARAM_NAME_ORDER_ID = "id"; // если у заказаа с этим айди статус 1, то ничег оне делать, он уже закрыт
    private static final String ATTR_NAME_MESSAGE = "message";
    private static final double DELTA_DISTANCE_USER_BIKE = 0.0010;
    private OrderService orderService = new OrderService();
    private BikeService bikeService = new BikeService();
    private TextManager textManager;

    @Override
    public Router execute(HttpServletRequest request) {
        request.removeAttribute(ATTR_NAME_MESSAGE);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);
        String message = null;
        int idOrder = Integer.parseInt(request.getParameter(PARAM_NAME_ORDER_ID));
        LocalTime returnTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));

        boolean flag = true;

        while (flag) {
            if (OrderValidator.isOrderFinished(idOrder)) {
                message = textManager.getProperty("message.orderAlreadyFinished");
                break;
            }
            try {
                Order finishedOrder = orderService.finOrderById(idOrder);
                orderService.finishOrder(idOrder, returnTime);
                Bike bike = bikeService.findBikeById(finishedOrder.getIdBike());
                session.setAttribute("userLatitude", bike.getLatitude() + DELTA_DISTANCE_USER_BIKE);
                session.setAttribute("userLongitude", bike.getLongitude() + DELTA_DISTANCE_USER_BIKE);
            } catch (ServiceException e) {
                logger.error("Can't execute FinishOrderCommand", e);
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }
            break;
        }

        request.setAttribute(ATTR_NAME_MESSAGE, message);
        return CommandEnum.SHOW_ALL_USER_ORDERS.getCommand().execute(request);
    }
}
