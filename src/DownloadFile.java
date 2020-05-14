package src;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The download class that download file from the source
 * 
 * @author Sahanon Phisetpakasit
 */
public class DownloadFile {

    /**
     * The url reader method that download file from the url
     * 
     * @throws MalformedURLException throw if t=the url is malformed
     * @throws IOException           throw if something went wrong when read the url
     */
    public static void urlReader() throws MalformedURLException, IOException {
        // map that contain the key = url and values = file path
        Map<String, String> pathMap = new HashMap<>();
        // put element to the map
        pathMap.put(Config.getInstance().getProperty("covid.confirmed.url"),
                Config.getInstance().getProperty("covid.confirmed.data"));
        pathMap.put(Config.getInstance().getProperty("covid.deaths.url"),
                Config.getInstance().getProperty("covid.deaths.data"));
        pathMap.put(Config.getInstance().getProperty("covid.recovered.url"),
                Config.getInstance().getProperty("covid.recovered.data"));
        for (String key : pathMap.keySet()) {
            // the existence file
            File existence = new File(pathMap.get(key));
            // if existence is exist then delete the exist file for download the new one
            if (existence.exists()) {
                existence.delete();
            }
            // read the url
            InputStream input = new URL(key).openStream();
            // download the file
            Files.copy(input, Paths.get(pathMap.get(key)));
        }
    }
}