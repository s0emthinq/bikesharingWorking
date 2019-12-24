package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.Card;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.service.CardService;
import by.me.bikesharing.servlet.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Forward to order bike command.
 */
public class ForwardToOrderBikeCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private BikeService bikeService = new BikeService();
    private CardService cardService = new CardService();
    private static final String ATTR_NAME_CURRENT_USER = "currentUser";
    private static final String ATTR_NAME_USER_CARDS_LIST = "userCardsList";

    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.orderBike");
        try {
            bikeService.updateMap("E:\\login\\src\\main\\webapp\\js\\data.json", request);
            bikeService.updateMap("E:\\login\\target\\login\\js\\data.json", request);
            String login = (String) request.getSession().getAttribute(ATTR_NAME_CURRENT_USER);
            List<Card> userCards = cardService.getAllUserCardsByLogin(login);
            request.setAttribute(ATTR_NAME_USER_CARDS_LIST, userCards);
        } catch (ServiceException e) {
            logger.error("Can't update bikes on map", e);
            page = ConfigurationManager.getProperty("path.page.error");
        }
        return new Router(RouteType.FORWARD, page);
    }
}
