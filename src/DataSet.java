package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The class that manage and keep the data of the covid 19
 * 
 * @author Sahanon Phisetpakasit
 */
public class DataSet {
    // collection of all the DataSeries
    Map<String, DataSeries> dataSeries;
    // name of the chart
    String name;
    // the last update date
    String lastUpdate;
    // the header in csvfile
    String[] header;
    // the date
    List<String> date;
    // the list that contain all data
    List<String[]> allData;

    /**
     * The class constructor
     */
    public DataSet(ReadFile rFile) {
        this.header = rFile.getHeader();
        this.date = new ArrayList<String>();
        for (int i = 4; i < this.header.length; i++) {
            date.add(header[i]);
        }
        this.allData = rFile.getAllData();
        this.lastUpdate = header[header.length - 1];
        this.name = rFile.getFile();
        this.dataSeries = makeDataSet();
    }

    /**
     * get the dataseries by the country name
     */
    DataSeries getDataSeries(String country) {
        return dataSeries.get(country);
    };

    /**
     * The method that create the map that key is the country name and values is the
     * dataseries of that country
     */
    private Map<String, DataSeries> makeDataSet() {
        Map<String, DataSeries> map = new TreeMap<String, DataSeries>();
        for (int i = 0; i < allData.size(); i++) {
            DataSeries x = new DataSeries(allData.get(i));
            if (x.region.isBlank())
                map.put(x.country + x.region, x);
            else
                map.put(x.country + " - " + x.region, x);
        }
        return map;
    }

    /**
     * get the all the country name
     * 
     * @return set of the country
     */
    public Set<String> getCountry() {
        return dataSeries.keySet();
    }

    /**
     * get the name of the file
     * 
     * @return name of the file
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the last update date
     * 
     * @return last update date
     */
    public String getLastUpdate() {
        return this.lastUpdate;
    }

    /**
     * get the date of the data
     * 
     * @return the date
     */
    public List<String> getDate() {
        return this.date;
    }

}

/**
 * inner class that make the dataseries
 */
class DataSeries {
    // country name
    String country;
    // region of the country
    String region;
    // latitude of the country
    double latitude;
    // longitude of the country
    double longitude;
    // list of the number of covid 19
    List<Integer> data;

    /**
     * class constructor
     * 
     * @param allData the data that want to manage
     */
    public DataSeries(String[] allData) {
        this.country = allData[1];
        this.region = allData[0];
        this.latitude = Double.parseDouble(allData[2]);
        this.longitude = Double.parseDouble(allData[3]);
        this.data = new ArrayList<Integer>();
        for (int i = 4; i < allData.length; i++) {
            data.add(Integer.parseInt(allData[i]));
        }

    }

    /**
     * get the list of number of the covid 19
     * 
     * @return
     */
    public List<Integer> getData() {
        return this.data;
    }

}
