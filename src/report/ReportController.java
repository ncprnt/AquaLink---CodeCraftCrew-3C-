package report;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ReportController {

    @FXML
    private TableView<Report> tableMyReports;

    @FXML
    private TableColumn<Report, Integer> colReportId;

    @FXML
    private TableColumn<Report, String> colReportTitle;

    @FXML
    private TableColumn<Report, String> colReportDate;

    @FXML
    private TableColumn<Report, String> colReportLocation;

    @FXML
    private TableColumn<Report, String> colReportDescription;

    @FXML
    private TableColumn<Report, String> colReportStatus;

    private ReportService reportService;

    public ReportController() {
        reportService = new ReportService("src/database/Report.xml");
    }

    @FXML
    public void initialize() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReportTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colReportDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReportLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colReportDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReportStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadReports();
    }

    private void loadReports() {
        try {
            List<Report> reports = reportService.loadReportsFromXML();
            tableMyReports.getItems().setAll(reports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
