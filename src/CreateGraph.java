package src;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Create graph class that create line chart and default chart
 * 
 * @author Sahanon Phisetpaksit
 */
public class CreateGraph {
    /**
     * The method that create the default chart that set every value to zero in
     * order to create the plain chart
     * 
     * @param data the data of the x axis
     * @return the chart
     */
    public XYChart.Series<String, Number> defaultChart(DataSet data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String date : data.getDate()) {
            series.getData().add(new XYChart.Data<>(date, 0));
        }
        series.setName("Select Country");
        return series;
    }

    /**
     * The create method for making linechart by adding the chart
     * 
     * @param chart the chart that want to create the line chart
     * @return the linechart
     */
    public LineChart<String, Number> createLineChart(XYChart.Series<String, Number> chart) {
        LineChart<String, Number> linechart = new LineChart<String, Number>(new CategoryAxis(), new NumberAxis());
        linechart.getData().add(chart);
        return linechart;
    }
}