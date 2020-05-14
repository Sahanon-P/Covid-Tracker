package src;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * The UI interface of the app Covid 19 tracker
 * 
 * @author Sahanon Phisetpakasit
 */
public class App extends Application {
    // The main layout
    private FlowPane root;
    // The creategraph attribute
    private CreateGraph cg;
    // Border of the current screen
    private Rectangle2D screenBounds;
    // linechart of the app
    private LineChart<String, Number> lineChart;
    // combobox of the country
    private ComboBox<String> countryBox;
    // list that contain the textfield
    private List<TextField> display = new ArrayList<>();
    // the dataset of the graph
    private DataSet defaultSet;
    // list that contain all the dataset
    private List<DataSet> dataSets = new ArrayList<>();

    /**
     * main method that will run the app     
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * The unimpement method of the application interface
     * The layout in javafx using flowpane
     */
    @Override
    public void start(Stage prime) throws Exception {
        // set the title of the app
        prime.setTitle("COVID19 Tracker");
        //set the bound of the screen
        screenBounds = Screen.getPrimary().getBounds();
        // when the app start download the csv
        DownloadFile.urlReader();
        // add all the dataset to the list
        dataSets.add(new DataSet(new ReadFile(Config.getInstance().getProperty("covid.confirmed.data"))));
        dataSets.add(new DataSet(new ReadFile(Config.getInstance().getProperty("covid.deaths.data"))));
        dataSets.add(new DataSet(new ReadFile(Config.getInstance().getProperty("covid.recovered.data"))));
        // set the flowpane to the initial component
        root = initComponent();
        // set the layout and layout size to the screen
        Scene scene = new Scene(root, screenBounds.getMaxX(), screenBounds.getMaxY());
        // set the screen
        prime.setScene(scene);
        // set screen to fullscreen
        prime.setFullScreen(true);
        // show the screen
        prime.show();
    }
    /**
     * The initial component that contain all the component that using in the app
     */
    private FlowPane initComponent() throws FileNotFoundException {
        FlowPane root = new FlowPane();
        // initialize the combobox
        this.countryBox = new ComboBox<>();
        // name label of the app
        Label nameLabel = new Label("Covid-19 Tracker");
        // label of text field
        Label label1 = new Label("confirmed :");
        Label label2 = new Label("deaths :");
        Label label3 = new Label("recoverd :");
        // textfield 
        TextField display1 = new TextField("0");
        TextField display2 = new TextField("0");
        TextField display3 = new TextField("0");
        // initialize the create graph method
        cg = new CreateGraph();
        // get the default data
        defaultSet = dataSets.get(0);
        // create the default chart for using in linechart
        XYChart.Series<String, Number> defaultChart = cg.defaultChart(defaultSet);
        // add the textfield to the list of textfield
        display.add(display1);
        display.add(display2);
        display.add(display3);
        // create the exit button
        Button exitButton = new Button("Exit");
        // set default chart to linechart
        lineChart = cg.createLineChart(defaultChart);
        // set the prompttext of combobox
        this.countryBox.setPromptText("Select country");
        // add the items from dataset to combobox
        this.countryBox.getItems().addAll(defaultSet.getCountry());
        // set the size of linechart
        this.lineChart.setPrefSize(screenBounds.getMaxX(), 750);
        // text of the date time of data
        Text dateText = new Text("Last updated: " + defaultSet.getLastUpdate());
        // reference text
        Text reference = new Text("Data projected using csv files obtained from The Humanitarian Data Exchange Website");
        // set the action of combobox
        this.countryBox.setOnAction(this::handleCountryBox);
        // set action to exit button
        exitButton.setOnAction(this::handleExit);
        // add component to the layout
        root.getChildren().addAll(nameLabel,this.countryBox, label1,display.get(0), label2,display.get(1), label3,display.get(2));
        // set the layput properties
        root.setAlignment(Pos.CENTER);
        root.setHgap(25.0);
        root.setVgap(10.0);
        // add the other element
        root.getChildren().addAll(this.lineChart, dateText, reference, exitButton);
        return root;
    }
    /**
     * The handle event of the combobox
     * that get the name of the country and draw the linechart of that country
     * @param event the event that occur
     */
    private void handleCountryBox(ActionEvent event) {
        // always clear chart when the event occur
        this.lineChart.getData().clear();
        // the name of the country
        String value = this.countryBox.getValue();
        // index values
        int index = 0;
        for (DataSet dataSet : dataSets) {
            try {
                // list of the number of data
                List<Integer> data = dataSet.getDataSeries(value).getData();
                // list of the date
                List<String> date = dataSet.getDate();
                // the initial chart
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                for (int i = 0; i < data.size(); i++) {
                    // create the chart
                    series.getData().add(new XYChart.Data<>(date.get(i), data.get(i)));
                }
                // set the name of the chart to the name of file
                series.setName(dataSet.getName());
                // add the chart to the linechart
                this.lineChart.getData().add(series);
                // set the latest of the data to the textfield
                this.display.get(index).setText(data.get(data.size() - 1).toString());
                index++;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * The handle event for exit
     * when press button exit the programm
     */
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }
}