package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.service.UserService;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Unblock user command.
 */
public class UnblockUserCommand implements ActionCommand {

    private static final String PARAM_NAME_ID = "id";
    private UserService userService = new UserService();

    @Override
    public Router execute(HttpServletRequest request) {
        long id = Long.valueOf(request.getParameter(PARAM_NAME_ID));
        try {
            userService.unblockUser(id);
        } catch (ServiceException e) {
            return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.error"));
        }
        return CommandEnum.SHOW_ALL_USERS.getCommand().execute(request);
    }
}
