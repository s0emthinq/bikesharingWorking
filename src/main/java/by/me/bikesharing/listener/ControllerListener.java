package by.me.bikesharing.listener;

import by.me.bikesharing.pool.CustomConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The type Controller listener.
 */
public class ControllerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CustomConnectionPool.INSTANCE.initPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        CustomConnectionPool.INSTANCE.destroyPool();
    }
}
