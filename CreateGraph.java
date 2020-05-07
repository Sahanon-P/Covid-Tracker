import java.util.List;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CreateGraph {
    public LineChart<String,Number> createSpecific(String[] data,String[] header){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of death");
        LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        series.setName(data[1]);
        for(int i = 4; i<data.length;i++){
            if(data.length > header.length){
                series.getData().add(new XYChart.Data(header[i], Double.parseDouble(data[i+1])));
            }
            else{
                series.getData().add(new XYChart.Data(header[i], Double.parseDouble(data[i])));
            }

        }
        lineChart.getData().add(series);
        return lineChart;
    }

    public LineChart<String,Number> createAll(List<String[]> data){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of death");
        LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis, yAxis);
        for(int i = 1;i<data.size();i++){
            if(data.get(i).length == data.get(0).length){
                XYChart.Series series = new XYChart.Series();
                for(int j = 4; j<data.get(i).length;j++){
                    series.getData().add(new XYChart.Data(data.get(0)[j],Double.parseDouble(data.get(i)[j])));
                }
                lineChart.getData().add(series);
            }
        }
        return lineChart;
    }
}