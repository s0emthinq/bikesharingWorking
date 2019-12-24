package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.service.UserService;
import by.me.bikesharing.servlet.Router;
import by.me.bikesharing.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Block user command.
 */
public class BlockUserCommand implements ActionCommand {

    private static final String PARAM_NAME_ID = "id";
    private static final String PARAM_NAME_MESSAGE = "message";
    private UserService userService = new UserService();
    private TextManager textManager;

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        textManager = new TextManager(language);
        request.removeAttribute(PARAM_NAME_MESSAGE);
        String message = textManager.getProperty("message.userBlocked");
        boolean isOk = true;

        String idAsString = request.getParameter(PARAM_NAME_ID);

        if (UserValidator.isParametersNullOrEmpty(idAsString)) {
            message = textManager.getProperty("message.parameters.empty");
            isOk = false;
        }

        if (!UserValidator.isIdValid(idAsString)) {
            message = textManager.getProperty("message.parameters.incorrectIdFormat");
            isOk = false;
        }

        long id = Long.valueOf(idAsString);

        if (!UserValidator.isUserExists(id)) {
            message = textManager.getProperty("message.parameters.noSuchUser");
            isOk = false;
        }

        if (!UserValidator.isUserActive(id)) {
            message = textManager.getProperty("message.userIsAlreadyBlocked");
            isOk = false;
        }

        if (UserValidator.isUserAdmin(id)) {
            message = textManager.getProperty("message.cantBlockAdmin");
            isOk = false;
        }

        if (isOk) {
            try {
                userService.blockUser(id);
            } catch (ServiceException e) {
                return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
            }
        }

        request.setAttribute(PARAM_NAME_MESSAGE, message);
        return CommandEnum.SHOW_ALL_USERS.getCommand().execute(request);
    }
}
