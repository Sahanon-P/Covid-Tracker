package src;

import java.io.*;
import java.util.Properties;

/**
 * The class that manage the propertoes of the file
 * 
 * @author Sahanon Phisetpakasit
 */
public class Config {
    // the name of the properties file
    private static String PROPERTIESFILES = "covid.properties";
    // instance of the class
    private static Config instance = null;
    // properties of the class
    private Properties props = null;

    /**
     * The constructor of the class when it create load the properties of the file
     */
    private Config() {
        loadProperties(PROPERTIESFILES);
    }

    /**
     * get the instance of the class using lazy initialization
     * 
     * @return the instance
     */
    public static Config getInstance() {
        // if it still not create the instznce
        if (instance == null) {
            // create the instance
            instance = new Config();
        }
        return instance;
    }

    /**
     * loading method of the propertie
     * 
     * @param filename the properties file
     */
    private void loadProperties(String filename) {
        props = new Properties();
        InputStream instream = null;
        ClassLoader loader = this.getClass().getClassLoader();
        instream = loader.getResourceAsStream(filename);
        if (instream == null) {
            System.err.println("Unable to open properties file " + filename);
            return;
        }
        try {
            // load the properties
            props.load(instream);
        } catch (IOException e) {
            System.err.println("Error reading properties file " + filename);
            System.err.println(e.getMessage());
        }
        try {
            instream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * get the file inside the properties
     * 
     * @param name the name of the properties that contain the wanted file
     * @return the filepath.
     */
    public String getProperty(String name) {
        return props.getProperty(name);
    }

}