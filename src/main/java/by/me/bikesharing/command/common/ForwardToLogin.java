package by.me.bikesharing.command.common;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Forward to login.
 */
public class ForwardToLogin implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("role") == null) {
            return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.login"));
        } else {
            int role = (int) request.getSession().getAttribute("role");
            if (role == 1) {
                return CommandEnum.SHOW_ALL_BIKES.getCommand().execute(request);
            } else {
                return CommandEnum.FORWARD_TO_ORDER_BIKE.getCommand().execute(request);
            }
        }
    }
}