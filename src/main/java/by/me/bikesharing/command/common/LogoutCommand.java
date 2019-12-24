package by.me.bikesharing.command.common;


import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Logout command.
 */
public class LogoutCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");
        Router router = new Router(RouteType.FORWARD,page);
// уничтожение сессии
        request.getSession().invalidate();
        return router;
    }
}
