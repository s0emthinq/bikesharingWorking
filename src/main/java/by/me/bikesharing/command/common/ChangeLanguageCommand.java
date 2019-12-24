package by.me.bikesharing.command.common;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.manager.TextManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Change language command.
 */
public class ChangeLanguageCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute("language");
        if ("en_US".equals(language)) {
            session.setAttribute("language", "ru_RU");
        } else {
            session.setAttribute("language", "en_US");
        }
        return new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.login"));
    }
}
