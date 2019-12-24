package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Forward to update bike form command.
 */
public class ForwardToUpdateBikeFormCommand implements ActionCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.updateBike");
        return new Router(RouteType.FORWARD, page);
    }
}
