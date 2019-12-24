package by.me.bikesharing.command.factory;


import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.common.EmptyCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.command.common.ForwardToLogin;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Command factory.
 */
public class CommandFactory {
    /**
     * Define command action command.
     *
     * @param request the request
     * @return the action command
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongCommand", "Command " + action + "  not found or wrong");
        }
        return current;
    }
}