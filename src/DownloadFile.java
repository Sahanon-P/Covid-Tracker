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

public class DownloadFile {

    public static void urlReader() throws MalformedURLException, IOException {
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put(Config.getInstance().getProperty("covid.confirmed.url"), Config.getInstance().getProperty("covid.confirmed.data"));
        pathMap.put(Config.getInstance().getProperty("covid.deaths.url"), Config.getInstance().getProperty("covid.deaths.data"));
        pathMap.put(Config.getInstance().getProperty("covid.recovered.url"), Config.getInstance().getProperty("covid.recovered.data"));
        for(String key : pathMap.keySet()) {
            File existence = new File(pathMap.get(key));
            if (existence.exists()) existence.delete(); /** check file date, if today , use the old, if not download new. */
            InputStream input =  new URL(key).openStream();
            Files.copy(input, Paths.get(pathMap.get(key)));
        }
    }
}