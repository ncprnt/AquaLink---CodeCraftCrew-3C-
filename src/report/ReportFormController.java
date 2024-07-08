package report;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class ReportFormController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField locationField;

    private ReportFormService reportFormService;

    public ReportFormController() {
        reportFormService = new ReportFormService("src/database/Report.xml");
    }

    @FXML
    private void handleSubmitReport(ActionEvent event) {
        String title = titleField.getText();
        LocalDate date = datePicker.getValue();
        String description = descriptionArea.getText();
        String location = locationField.getText();

        if (title.isEmpty() || date == null || description.isEmpty() || location.isEmpty()) {
            // Show error message using Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Submit the report
        reportFormService.submitReport(new ReportForm(0, title, date.toString(), location, description, "Pending"));

        // Show success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Report submitted successfully!");
        successAlert.showAndWait();

        // Close the form
        titleField.clear();
        datePicker.setValue(null);
        locationField.clear();
        descriptionArea.clear();
    }
}
