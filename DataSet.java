import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DataSet {
    // collection of all the DataSeries
    Map<String, DataSeries> dataSeries;
    String name;
    String lastUpdate;
    String[] header;
    List<String> date; // or make this a DataSeries
    List<String[]> allData;

    public DataSet(ReadFile rFile) {
        this.header = rFile.getHeader();
        this.date = new ArrayList<String>();
        for(int i = 4; i<this.header.length; i++){
            date.add(header[i]);
        }
        this.allData = rFile.getAllData();
        this.lastUpdate = header[header.length - 1];
        this.name = rFile.toString();
        this.dataSeries = makeDataSet();
    }

    // and maybe, if it makes the code simpler. Get the country-level data series.
    DataSeries getDataSeries(String country) {
        return dataSeries.get(country);
    };
    private Map<String,DataSeries> makeDataSet() {
        Map<String,DataSeries> map = new TreeMap<String,DataSeries>();
        for (int i = 0; i < allData.size(); i++) {
            DataSeries x = new DataSeries(allData.get(i));
            if (x.region.isBlank()) map.put(x.country + x.region, x);
            else map.put(x.country + " - " + x.region, x);
        }
        return map;
    }
    public Set<String> getCountry(){
        return dataSeries.keySet();
    }
    public String getName(){
        return this.name;
    }
    public String getLastUpdate(){
        return this.lastUpdate;
    }
    public List<String> getDate(){
        return this.date;
    }

}

class DataSeries {
    String country;
    String region;
    double latitude;
    double longitude;
    List<Integer> data;

    public DataSeries(String[] allData) {
        this.country = allData[1];
        this.region = allData[0];
        this.latitude = Double.parseDouble(allData[2]);
        this.longitude = Double.parseDouble(allData[3]);
        this.data = new ArrayList<Integer>();
        for(int i = 4; i<allData.length; i++){
            data.add(Integer.parseInt(allData[i]));
        }

    }

    public List<Integer> getData() {
        return this.data;
    }

}
