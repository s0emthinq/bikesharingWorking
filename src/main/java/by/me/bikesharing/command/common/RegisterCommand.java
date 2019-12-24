package by.me.bikesharing.command.common;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.UserService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Register command.
 */
public class RegisterCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private static final String ATTR_NAME_MESSAGE = "message";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_REPEATED_PASSWORD = "repeated_password";
    private static final String PARAM_NAME_EMAIL = "email";
    private TextManager textManager;
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) {


        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String repeatedPassword = request.getParameter(PARAM_NAME_REPEATED_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);

        String message = "";

        boolean flag = true;

        while (flag) {

            if (UserValidator.isParametersNullOrEmpty(login, password, repeatedPassword, email)) {
                message = textManager.getProperty("message.parameters.empty");
                break;
            }

            if (!UserValidator.isLoginValid(login)) {
                message = textManager.getProperty("message.parameters.incorrectLogin");
                break;
            }

            if (!UserValidator.isPasswordValid(password)) {
                message = textManager.getProperty("message.parameters.incorrectPassword");
                break;
            }

            if (!UserValidator.isEmailValid(email)) {
                message = textManager.getProperty("message.parameters.incorrectEmail");
                break;
            }

            if (!UserValidator.isPasswordsSame(password, repeatedPassword)) {
                message = textManager.getProperty("message.parameters.passwordsNotTheSame");
                break;
            }

            if (UserValidator.isUserExists(login)) {
                message = textManager.getProperty("message.loginIsTaken");
                break;
            }
            try {
                userService.registerUser(login, password, email);
            } catch (ServiceException e) {
                logger.error("Cant' execute register user command", e);
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }
            return CommandEnum.LOGIN.getCommand().execute(request);
        }
        request.setAttribute(ATTR_NAME_MESSAGE, message);
        return CommandEnum.FORWARD_TO_REGISTER.getCommand().execute(request);
    }
}
