package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Forward to add bike form command.
 */
public class ForwardToAddBikeFormCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.addBike");
        return new Router(RouteType.FORWARD, page);
    }
}
