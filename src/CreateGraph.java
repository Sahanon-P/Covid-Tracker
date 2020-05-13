package src;

import java.util.List;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CreateGraph {
    public XYChart.Series<String, Number> defaultChart(DataSet data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(String date: data.getDate()) {series.getData().add(new XYChart.Data<>(date, 0));}
        series.setName("Select Country");
        return series;
    }
    public LineChart<String,Number> createLineChart(XYChart.Series<String,Number> chart ){
        LineChart<String,Number> linechart = new LineChart<String, Number>(new CategoryAxis(), new NumberAxis());
        linechart.getData().add(chart);
        return linechart;
    }
    public XYChart.Series<String,Number> makeChart(List<DataSet> allData ,String key){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(DataSet ds : allData){
            List<Integer> data = ds.getDataSeries(key).getData();
            List<String> date = ds.getDate();
            for(int i = 0; i < data.size(); i++) {series.getData().add(new XYChart.Data<>(date.get(i), data.get(i)));}
            series.setName(ds.getName());
        }
        return series;
    }
}