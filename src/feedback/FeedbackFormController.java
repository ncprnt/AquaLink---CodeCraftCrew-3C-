package feedback;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class FeedbackFormController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField projectNameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField locationField;

    private FeedbackFormService feedbackFormService;

    public FeedbackFormController() {
        this.feedbackFormService = new FeedbackFormService();
    }

    @FXML
    private void handleSubmitReport(ActionEvent event) {
        String fullName = fullNameField.getText();
        String projectName = projectNameField.getText();
        LocalDate date = datePicker.getValue();
        String description = descriptionArea.getText();
        String location = locationField.getText();

        if (fullName.isEmpty() || projectName.isEmpty() || date == null || description.isEmpty() || location.isEmpty()) {
            // Show error message or handle validation
            return;
        }

        Feedback feedback = new Feedback(0, fullName, projectName, date, location, description);
        feedbackFormService.saveFeedbackToXML(feedback);

        // Show success message using Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Feedback submitted successfully!");
        alert.showAndWait();

        // Clear the form fields
        fullNameField.clear();
        projectNameField.clear();
        datePicker.setValue(null);
        locationField.clear();
        descriptionArea.clear();
    }
}
