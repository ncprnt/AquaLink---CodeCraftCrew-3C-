package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class ProjectFormController {

    @FXML
    private TextField projectNameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField locationField;

    private ProjectFormService projectFormService;

    public ProjectFormController() {
        projectFormService = new ProjectFormService("src/database/Project.xml");
    }

    @FXML
    private void handleSubmitProject(ActionEvent event) {
        String projectName = projectNameField.getText();
        LocalDate date = datePicker.getValue();
        String description = descriptionArea.getText();
        String location = locationField.getText();

        if (projectName.isEmpty() || date == null || description.isEmpty() || location.isEmpty()) {
            // Show error message or handle empty fields
            return;
        }

        String status = "Pending"; // Set default status

        ProjectForm projectForm = new ProjectForm(0, projectName, date.toString(), location, description, status);
        projectFormService.submitProject(projectForm);

        // Show success message using Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Project proposal submitted successfully!");
        alert.showAndWait();

        // Clear form fields
        projectNameField.clear();
        datePicker.setValue(null);
        locationField.clear();
        descriptionArea.clear();
    }
}
