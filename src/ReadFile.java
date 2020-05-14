package src;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;

/**
 * The class that read the file from csv
 * 
 * @author Sahanon Phisetpaksit
 */
public class ReadFile {
    // the file name
    private String file;
    // list of the readed data
    private List<String[]> list;

    /**
     * class constructor
     */
    public ReadFile(String file) {
        list = new ArrayList<String[]>();
        this.file = file;
        readFile(file);
    }

    /**
     * the read method that read the file from the csv and add to the list
     * 
     * @param sourceFile the file that want to read
     */
    private void readFile(String sourceFile) {
        String[] readline = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(sourceFile))) {
            while ((readline = csvReader.readNext()) != null) {
                this.list.add(readline);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * get the data of the covid 19
     * 
     * @return the data of the covid 19
     */
    public List<String[]> getAllData() {
        return this.list.subList(1, list.size());
    }

    /**
     * get the header of the csv
     * 
     * @return the header of the csv
     */
    public String[] getHeader() {
        return this.list.get(0);
    }

    /**
     * get the filename
     * 
     * @return filename
     */
    public String getFile() {
        return this.file;
    }
}