package by.me.bikesharing.filter;


import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.manager.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Protect admin command filter.
 */
public class ProtectAdminCommandFilter implements Filter {

    private static final String PARAM_NAME_COMMAND = "command";
    private static final String ATTR_NAME_CURRENT_ROLE = "role";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String command = servletRequest.getParameter(PARAM_NAME_COMMAND);
        if (command != null) {
            command = command.toUpperCase();
            if (command.equals(CommandEnum.SHOW_ALL_BIKES.toString())
                    || command.equals(CommandEnum.FORWARD_TO_UPDATE_BIKE.toString())
                    || command.equals(CommandEnum.UPDATE_BIKE.toString())
                    || command.equals(CommandEnum.FORWARD_TO_ADD_BIKE.toString())
                    || command.equals(CommandEnum.ADD_BIKE.toString())
                    || command.equals(CommandEnum.DELETE_BIKE.toString())
                    || command.equals(CommandEnum.SHOW_ALL_USERS.toString())
                    || command.equals(CommandEnum.BLOCK_USER.toString())
                    || command.equals(CommandEnum.UNBLOCK_USER.toString())) {
                HttpSession session = ((HttpServletRequest) servletRequest).getSession();
                if (session.getAttribute(ATTR_NAME_CURRENT_ROLE) != null) {
                    int role = (int) session.getAttribute(ATTR_NAME_CURRENT_ROLE);
                    if (role != 1) {
                        String page = ConfigurationManager.getProperty("path.page.login");
                        servletRequest.getRequestDispatcher(page).forward(servletRequest,servletResponse);
                        return;
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
