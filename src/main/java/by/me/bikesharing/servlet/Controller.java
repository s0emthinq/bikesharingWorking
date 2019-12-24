package by.me.bikesharing.servlet;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.factory.CommandFactory;

import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.manager.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * The type Controller.
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Router router;
        CommandFactory client = new CommandFactory();
        ActionCommand command = client.defineCommand(request);
        router = command.execute(request);

        if (router != null) {
            if (router.getRouteType() == RouteType.FORWARD) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPage());

                try {
                    dispatcher.forward(request, response);
                } catch (ServletException e) {
                    System.out.println("Servlet Exception");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("IOException");
                }
            } else {
                try {
                    response.sendRedirect(request.getContextPath() + router.getPage());
                } catch (IOException e) {
                    System.out.println("SendRedirect IO Exception");
                }
            }
        } else {
            try {
                response.sendRedirect(ConfigurationManager.getProperty("path.page.error"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}