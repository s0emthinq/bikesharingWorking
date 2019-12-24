package by.me.bikesharing.command.admin;

import by.me.bikesharing.command.ActionCommand;
import by.me.bikesharing.command.client.CommandEnum;
import by.me.bikesharing.entity.RouteType;
import by.me.bikesharing.exception.ServiceException;
import by.me.bikesharing.service.BikeService;
import by.me.bikesharing.manager.ConfigurationManager;
import by.me.bikesharing.servlet.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Delete bike command.
 */
public class DeleteBikeCommand implements ActionCommand {

    private static final BikeService logic = new BikeService();

    private static final String PARAM_BIKE_ID = "id";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        try {
            int id = Integer.parseInt(request.getParameter(PARAM_BIKE_ID));
            logic.deleteBikeById(id);
            CommandEnum.SHOW_ALL_BIKES.getCommand().execute(request);
            router = new Router(RouteType.FORWARD, ConfigurationManager.getProperty("path.page.bikes"));
            request.setAttribute("message", "Bike successfully deleted");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return router;
    }


}
