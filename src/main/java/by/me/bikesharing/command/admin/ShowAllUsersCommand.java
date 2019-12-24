package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.dao.UserDao;
import by.me.bikesharing.dao.impl.UserDaoImpl;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.entity.User;
import by.me.bikesharing.exception.DaoException;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.service.UserService;
import by.me.bikesharing.servlet.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Show all users command.
 */
public class ShowAllUsersCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private UserService userService = new UserService();
    private static final String ATTR_NAME_BIKES_LIST = "usersList";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        List<User> users = null;
        try {
            users = userService.readAllUsers();
            page = ConfigurationManager.getProperty("path.page.users");
        } catch (ServiceException e) {
            logger.info("Can't perform ShowAllUsers command", e);
            page = ConfigurationManager.getProperty("path.page.error");
        }
        request.setAttribute(ATTR_NAME_BIKES_LIST, users);
        return new Router(RouteType.FORWARD, page);
    }
}
