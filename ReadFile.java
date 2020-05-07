import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public String[] getData(String sourceFile,int index){
        String line;
        List<String[]> allData = new ArrayList<String[]>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(sourceFile))) {
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                allData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allData.get(index);
    }
    public List<String[]> getAllData(String sourceFile){
        String line;
        List<String[]> allData = new ArrayList<String[]>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(sourceFile))) {
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                allData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allData;
    }

}