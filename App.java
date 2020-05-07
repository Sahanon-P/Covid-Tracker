import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class App extends Application {
    private FlowPane root;
    private String csvFile = "resource/time_series_covid19_deaths_global.csv";
    private ReadFile rFile = new ReadFile();
    private CreateGraph cg = new CreateGraph();
    private ComboBox<String> dataType;
    private ComboBox<Country> countryBox;
    LineChart<String,Number> lineChart;

    @Override
    public void start(Stage stage) throws Exception {
        root = new FlowPane();
        FlowPane component = initComponent();
        root.getChildren().add(component);
        stage.setScene(new Scene(root,700,500));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    public FlowPane initComponent(){
        FlowPane root = new FlowPane();
        dataType = new ComboBox<String>();
        dataType.getItems().add("All Data");
        dataType.getItems().add("Specific Data");
        dataType.setValue("All Data");
        dataType.setOnAction(this::dataTypeHandle);
        countryBox = new ComboBox<Country>();
        countryBox.setOnAction(this::countryHandle);
        root.getChildren().addAll(dataType,countryBox);
        return root;
    }
    public void dataTypeHandle(ActionEvent event){
        if(dataType.getValue().equals("All Data")){
            countryBox.getItems().clear();
            List<String[]> data = rFile.getAllData(csvFile);
            lineChart = cg.createAll(data);
            root.getChildren().add(lineChart);
        }
        else if(dataType.getValue().equals("Specific Data")){
            Country[] array = Country.getArray();
            countryBox.getItems().addAll(array);
        }
    }
    public void countryHandle(ActionEvent event){
        root.getChildren().remove(lineChart);
        int index = countryBox.getValue().getIndex();
        String[] data = rFile.getData(csvFile, index);
        String[] header =  rFile.getData(csvFile, 0);
        lineChart = cg.createSpecific(data, header);
        root.getChildren().add(0,lineChart);
        
    }

}