package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.service.OrderService;
import by.me.bikesharing.servlet.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Show all users orders.
 */
public class ShowAllUsersOrders implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private OrderService orderService = new OrderService();
    private static final String ATTR_NAME = "currentUser";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute(ATTR_NAME);
        try {
            List userOrders = orderService.getUserOrdersByLogin(currentUser);
            request.setAttribute("userOrders", userOrders);
        } catch (ServiceException e) {
            logger.error("Can't perform command ShowAllUsersOrders");
            return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.err    or"));
        }
        return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.userOrders"));
    }

}
