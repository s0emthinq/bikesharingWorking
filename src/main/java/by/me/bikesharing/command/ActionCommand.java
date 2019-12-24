package by.me.bikesharing.command;

import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The interface Action command.
 */
public interface ActionCommand {
    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);
}