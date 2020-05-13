import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    private FlowPane root;
    private CreateGraph cg;
    private Rectangle2D screenBounds;
    private LineChart<String, Number> lineChart;
    private ComboBox<String> countryBox;
    private List<TextField> display = new ArrayList<>();
    private DataSet defaultSet;
    private List<DataSet> dataSets = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage prime) throws Exception {
        prime.setTitle("COVID19 Data Visualization 1.0");
        screenBounds = Screen.getPrimary().getBounds();
        DownloadFile.urlReader();

        dataSets.add(new DataSet(new ReadFile(Config.getInstance().getProperty("covid.confirmed.data"))));
        dataSets.add(new DataSet(new ReadFile(Config.getInstance().getProperty("covid.deaths.data"))));
        dataSets.add(new DataSet(new ReadFile(Config.getInstance().getProperty("covid.recovered.data"))));

        root = initComponent();
        Scene scene = new Scene(root, screenBounds.getMaxX(), screenBounds.getMaxY());
        scene.getStylesheets().add("tracker/stylesheet/stylesheet.css");
        prime.setScene(scene);
        prime.setFullScreen(true);
        prime.show();
    }

    private FlowPane initComponent() throws FileNotFoundException {
        this.countryBox = new ComboBox<>();
        TextField display1 = new TextField("0");
        TextField display2 = new TextField("0");
        TextField display3 = new TextField("0");
        cg = new CreateGraph();
        defaultSet = dataSets.get(0);
        XYChart.Series<String, Number> defaultChart = cg.defaultChart(defaultSet);
        display.add(display1);
        display.add(display2);
        display.add(display3);
        Button exitButton = new Button("Exit");
        lineChart = cg.createLineChart(defaultChart);
        this.countryBox.setPromptText("Select country");
        this.countryBox.getItems().addAll(defaultSet.getCountry());
        this.lineChart.setPrefSize(screenBounds.getMaxX(), 750);
        Text dateText = new Text("Last updated: " + defaultSet.getLastUpdate());
        Text reference = new Text(
                "Data projected using csv files obtained from The Humanitarian Data Exchange Website");
        this.countryBox.setOnAction(this::handleBox);
        exitButton.setOnAction(this::handleExit);
        FlowPane root = new FlowPane();
        root.getChildren().addAll(this.countryBox, display.get(0), display.get(1), display.get(2));
        root.setAlignment(Pos.CENTER);
        root.setHgap(25.0);
        root.setVgap(10.0);
        root.getChildren().addAll(this.lineChart, dateText, reference, exitButton);
        return root;
    }

    private void handleBox(ActionEvent event) {
        this.lineChart.getData().clear();
        String value = this.countryBox.getValue();
        int index = 0;
        for (DataSet loop : dataSets) {
            try {
                List<Integer> data = loop.getDataSeries(value).getData();
                List<String> date = loop.getDate();
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                for (int i = 0; i < data.size(); i++) {
                    series.getData().add(new XYChart.Data<>(date.get(i), data.get(i)));
                }
                series.setName(loop.getName());
                this.lineChart.getData().add(series);
                this.display.get(index).setText(data.get(data.size() - 1).toString());
                index++;
            } catch (NullPointerException e) {
                /**  */
            }
        }
    }

    private void handleExit(ActionEvent event) {
        Platform.exit();
    }
}