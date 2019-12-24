package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.CardService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.CardValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Link card command.
 */
public class LinkCardCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private CardService cardService = new CardService();
    private static final String ATTR_NAME_MESSAGE = "message";
    private static final String ATTR_NAME_CURRENT_USER = "currentUser";
    private static final String PARAM_NAME_SERIAL_NUMBER = "serial_number";

    private TextManager textManager;

    @Override
    public Router execute(HttpServletRequest request) {
        String serialNumber = request.getParameter(PARAM_NAME_SERIAL_NUMBER);
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        String login = (String) session.getAttribute(ATTR_NAME_CURRENT_USER);
        textManager = new TextManager(language);

        String message = textManager.getProperty("message.cardLinked");

        boolean flag = true;

        while (flag) {

            if (CardValidator.isParametersNullOrEmpty(serialNumber)) {
                message = textManager.getProperty("message.parameters.empty");
                break;
            }
            if (!CardValidator.isSerialNumberValid(serialNumber) || CardValidator.isCardExist(serialNumber)) {
                message = textManager.getProperty("message.parameters.invalidSerialNumber");
                break;
            }

            try {
                cardService.linkUserCard(login, serialNumber);
            } catch (ServiceException e) {
                logger.info("Can't link card", e);
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }
            break;
        }
        request.setAttribute(ATTR_NAME_MESSAGE, message);

        return CommandEnum.FORWARD_TO_LINK_CARD.getCommand().execute(request);
    }
}
