package src;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;

public class ReadFile {
    private String file;
    private List<String[]> list;

    public ReadFile(String file) {
        list = new ArrayList<String[]>();
        this.file = file;
        readFile(file);
    }
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

    public List<String[]> getAllData() {
        return this.list.subList(1, list.size());
    }

    public String[] getHeader() {
        return this.list.get(0);
    }
    public String getFile(){
        return this.file;
    }
}