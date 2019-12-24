package by.me.bikesharing.command.common;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.service.UserService;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The type Login command.
 */
public class LoginCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final UserService userService = new UserService();
    private TextManager textManager;

    @Override
    public Router execute(HttpServletRequest request) {

        String page = ConfigurationManager.getProperty("path.page.login");
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String message = null;
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);
        double[] coordinates = UserService.generateRandomCoordinatesWithinMinsk();
        session.setAttribute("userLatitude", coordinates[0]);
        session.setAttribute("userLongitude", coordinates[1]);

        boolean flag = true;

        while (flag) {

            if (UserValidator.isParametersNullOrEmpty(login, password)) {
                message = textManager.getProperty("message.parameters.empty");
                break;
            }

            if (!UserValidator.isLoginValid(login)) {
                message = textManager.getProperty("message.parameters.incorrectLogin");
                break;
            }

            if (!UserValidator.isPasswordValid(login)) {
                message = textManager.getProperty("message.parameters.incorrectPassword");
                break;
            }

            if (!userService.isLoginMatchesPassword(login, password)) {
                message = textManager.getProperty("message.incorrectLoginOrPassword");
                break;
            }

            User currentUser = null;
            try {
                currentUser = userService.findUserByLogin(login);
            } catch (ServiceException e) {
                logger.error(e);
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }

            if (currentUser.getRole() == 1) {
                session.setAttribute("currentUser", login);
                session.setAttribute("role", currentUser.getRole());
                return CommandEnum.SHOW_ALL_BIKES.getCommand().execute(request);
            } else {
                session.setAttribute("currentUser", login);
                session.setAttribute("role", currentUser.getRole());
                return CommandEnum.FORWARD_TO_ORDER_BIKE.getCommand().execute(request);
            }

        }
        request.setAttribute("message", message);
        return new Router(RouteType.FORWARD, page);
    }
}