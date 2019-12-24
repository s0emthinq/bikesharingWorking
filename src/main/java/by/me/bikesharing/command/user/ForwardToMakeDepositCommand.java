package by.me.bikesharing.command.user;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Forward to make deposit command.
 */
public class ForwardToMakeDepositCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.makeDeposit"));
    }
}
