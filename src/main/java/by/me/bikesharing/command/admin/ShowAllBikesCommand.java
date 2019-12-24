package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.entity.Bike;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

/**
 * The type Show all bikes command.
 */
public class ShowAllBikesCommand implements ActionCommand {
    private static final BikeService service = new BikeService();
    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        try {
            List<Bike> bikes = service.readAllBikes();
            request.setAttribute("bikesList", bikes);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        page = ConfigurationManager.getProperty("path.page.bikes");
        return new Router(RouteType.FORWARD, page);
    }
}
