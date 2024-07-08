package progress;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class ProgressFormController {

    @FXML
    private TextField projectNameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField locationField;

    private ProgressFormService progressFormService;

    public ProgressFormController() {
        progressFormService = new ProgressFormService("src/database/Progress.xml");
    }

    @FXML
    private void handleSubmitReport(ActionEvent event) {
        String title = projectNameField.getText();
        LocalDate date = datePicker.getValue();
        String description = descriptionArea.getText();
        String location = locationField.getText();

        if (title.isEmpty() || date == null || description.isEmpty() || location.isEmpty()) {
            // Show error message or handle validation
            return;
        }

        progressFormService.submitReport(new ProgressForm(0, title, date.toString(), location, description, "Pending"));

        // Show success message using Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Progress report submitted successfully!");
        alert.showAndWait();

        // Clear form fields
        projectNameField.clear();
        datePicker.setValue(null);
        locationField.clear();
        descriptionArea.clear();
    }
}
