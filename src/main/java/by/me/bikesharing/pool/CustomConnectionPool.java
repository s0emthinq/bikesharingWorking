package by.me.bikesharing.pool;


import by.me.bikesharing.manager.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * The enum Custom connection pool.
 */
public enum CustomConnectionPool {

    /**
     * Instance custom connection pool.
     */
    INSTANCE;

    private final Logger logger = LogManager.getLogger();

    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> usedConnections;

    private static final String DATABASE_URL = "url";
    private static final String DATABASE_USER = "user";
    private static final String DATABASE_PASSWORD = "password";
    private static final String DATABASE_CHARACTER_ENCODING = "characterEncoding";
    private static final String DATABASE_USE_UNICODE = "useUnicode";
    private static final String DATABASE_AUTO_RECONNECT = "autoReconnect";
    private static final String DATABASE_SSL = "useSSL";
    private static final String DATABASE_SERVER_TIMEZONE = "serverTimezone";
    private static final String DATABASE_POOL_SIZE = "poolSize";
    private static final String DATABASE_USE_JDBC_COMPLIANT_TIMEZONE_SHIFT = "useJDBCCompliantTimezoneShift";

    private static final String URL = DatabaseManager.getProperty(DATABASE_URL);
    private static final String USER = DatabaseManager.getProperty(DATABASE_USER);
    private static final String PASSWORD = DatabaseManager.getProperty(DATABASE_PASSWORD);
    private static final String CHARACTER_ENCODING = DatabaseManager.getProperty(DATABASE_CHARACTER_ENCODING);
    private static final String USE_UNICODE = DatabaseManager.getProperty(DATABASE_USE_UNICODE);
    private static final String AUTO_RECONNECT = DatabaseManager.getProperty(DATABASE_AUTO_RECONNECT);
    private static final String SSL = DatabaseManager.getProperty(DATABASE_SSL);
    private static final String SERVER_TIMEZONE = DatabaseManager.getProperty(DATABASE_SERVER_TIMEZONE);
    private static final String USE_JDBC_COMPLIANT_TIMEZONE_SHIFT
            = DatabaseManager.getProperty(DATABASE_USE_JDBC_COMPLIANT_TIMEZONE_SHIFT);
    private final int POOL_SIZE = Integer.valueOf(DatabaseManager.getProperty(DATABASE_POOL_SIZE));

    CustomConnectionPool() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
            usedConnections = new ArrayDeque<>();
        } catch (ClassNotFoundException e) {
            logger.error("Can't download driver for connection", e);
        }
        logger.info("Connection pool was successfully created.");
    }

    /**
     * Init pool.
     */
    public void initPool() {
        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        properties.put("characterEncoding", CHARACTER_ENCODING);
        properties.put("useUnicode", USE_UNICODE);
        properties.put("autoReconnect", AUTO_RECONNECT);
        properties.put("SSL", SSL);
        properties.put("serverTimezone", SERVER_TIMEZONE);
        properties.put("useJDBCCompliantTimezoneShift", USE_JDBC_COMPLIANT_TIMEZONE_SHIFT);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.offer(new ProxyConnection(DriverManager.getConnection(URL, properties)));
            } catch (SQLException e) {
                e.printStackTrace(); //fixme
            }
        }
        logger.info("Connection pool was successfully initialised.Pool size: " + freeConnections.size());
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Can't get connection");
        }

        return connection;

    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    public void releaseConnection(ProxyConnection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            usedConnections.remove(connection);
            freeConnections.offer(connection);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Destroy pool.
     */
    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                e.printStackTrace(); //fixme
            }
        }
        deregisterDrivers();
        logger.info("Connection pool was successfully destroyed.");

    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Can't unregister driver");
            }
        });
        logger.info("All drivers where successfully unregistered.");
    }
}
