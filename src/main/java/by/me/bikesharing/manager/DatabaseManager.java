package by.me.bikesharing.manager;

import java.util.ResourceBundle;

/**
 * The type Database manager.
 */
public class DatabaseManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("property.database");

    private DatabaseManager() {
    }

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
