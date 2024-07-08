package progress;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProgressController {

    @FXML
    private TableView<Progress> tableMyProgress;

    @FXML
    private TableColumn<Progress, Integer> colProgressId;

    @FXML
    private TableColumn<Progress, String> colReportProjectName;

    @FXML
    private TableColumn<Progress, String> colProgressDate;

    @FXML
    private TableColumn<Progress, String> colProgressLocation;

    @FXML
    private TableColumn<Progress, String> colProgresDescription;

    private ProgressService progressService;

    public ProgressController() {
        progressService = new ProgressService("src/database/Progress.xml");
    }

    @FXML
    public void initialize() {
        colProgressId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReportProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        colProgressDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProgressLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colProgresDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadReports();
    }

    private void loadReports() {
        try {
            List<Progress> reports = progressService.loadReportsFromXML();
            tableMyProgress.getItems().setAll(reports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
