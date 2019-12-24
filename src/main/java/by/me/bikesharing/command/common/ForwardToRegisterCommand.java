package by.me.bikesharing.command.common;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Forward to register command.
 */
public class ForwardToRegisterCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.register"));
    }
}
