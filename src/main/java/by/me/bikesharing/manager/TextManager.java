package by.me.bikesharing.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type Text manager.
 */
public class TextManager {
    private ResourceBundle bundle;

    /**
     * Instantiates a new Text manager.
     *
     * @param language the language
     */
    public TextManager(String language) {
        if (language.equals("en_US")) {
            bundle = ResourceBundle.getBundle("property.text", new Locale("en", "US"));
        } else {
            bundle = ResourceBundle.getBundle("property.text", new Locale("ru", "RU"));
        }
    }

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return bundle.getString(key);
    }

}
