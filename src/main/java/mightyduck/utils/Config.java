package mightyduck.utils;

/**
 * Utility class that contains configuration constants.
 */
public class Config {

    /**
     * The URL to the user guide for the application.
     */
    public static final String GUIDE_LINK = "https://quangthangisme.github.io/ip/";

    /**
     * The path to the data file of the application.
     */
    public static final String STORAGE_PATH = "./data/mightyduck.txt";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Config() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
}
