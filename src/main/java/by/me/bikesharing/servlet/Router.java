package by.me.bikesharing.servlet;

import by.me.bikesharing.entity.RouteType;

/**
 * The type Router.
 */
public class Router {

    private RouteType routeType;
    private String page;

    /**
     * Instantiates a new Router.
     */
    public Router() {

    }

    /**
     * Instantiates a new Router.
     *
     * @param routeType the route type
     * @param page      the page
     */
    public Router(RouteType routeType, String page) {
        this.routeType = routeType;
        this.page = page;
    }

    /**
     * Gets route type.
     *
     * @return the route type
     */
    public RouteType getRouteType() {
        return routeType;
    }

    /**
     * Sets route type.
     *
     * @param routeType the route type
     */
    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }
}
