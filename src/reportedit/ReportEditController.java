package reportedit;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class ReportEditController {

    @FXML
    private TableView<ReportEdit> tableMyReports;

    @FXML
    private TableColumn<ReportEdit, Integer> colReportId;

    @FXML
    private TableColumn<ReportEdit, String> colReportTitle;

    @FXML
    private TableColumn<ReportEdit, String> colReportDate;

    @FXML
    private TableColumn<ReportEdit, String> colReportLocation;

    @FXML
    private TableColumn<ReportEdit, String> colReportDescription;

    @FXML
    private TableColumn<ReportEdit, String> colReportStatus;

    @FXML
    private TableColumn<ReportEdit, String> colAction;

    private ReportEditService reportService;

    public ReportEditController() {
        reportService = new ReportEditService("src/database/Report.xml");
    }

    @FXML
    public void initialize() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReportTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colReportDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReportLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colReportDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReportStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Enable editing for the status column
        tableMyReports.setEditable(true);
        colReportStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add custom action column
        colAction.setCellFactory(actionCellFactory);

        loadReports();
    }

    private void loadReports() {
        try {
            List<ReportEdit> reports = reportService.loadReportsFromXML();
            tableMyReports.getItems().setAll(reports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Callback<TableColumn<ReportEdit, String>, TableCell<ReportEdit, String>> actionCellFactory =
            new Callback<>() {
                @Override
                public TableCell<ReportEdit, String> call(final TableColumn<ReportEdit, String> param) {
                    final TableCell<ReportEdit, String> cell = new TableCell<>() {

                        final ComboBox<String> comboBox = new ComboBox<>();
                        final Button deleteButton = new Button("Delete");

                        {
                            comboBox.setItems(FXCollections.observableArrayList("Pending", "Accepted", "Rejected"));
                            comboBox.setStyle("-fx-background-color: white; -fx-border-color: #c9c9c9; -fx-border-radius: 10; -fx-background-radius: 10;");
                            comboBox.setOnAction(event -> {
                                ReportEdit report = getTableView().getItems().get(getIndex());
                                String newStatus = comboBox.getValue();
                                report.setStatus(newStatus);
                                reportService.updateReportStatus(report);
                                tableMyReports.refresh(); // Refresh the table to show the updated status
                            });

                            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10;");
                            deleteButton.setOnAction(event -> {
                                ReportEdit report = getTableView().getItems().get(getIndex());
                                reportService.deleteReport(report.getId());
                                tableMyReports.getItems().remove(report);
                            });
                        }

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                ReportEdit report = getTableView().getItems().get(getIndex());
                                comboBox.setValue(report.getStatus());
                                HBox hbox = new HBox(10, comboBox, deleteButton); // Add spacing
                                hbox.setSpacing(10); // Set spacing between ComboBox and Button
                                setGraphic(hbox);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
}
