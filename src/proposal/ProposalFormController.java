package proposal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ProposalFormController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField locationField;

    private ProposalFormService proposalFormService;

    public ProposalFormController() {
        proposalFormService = new ProposalFormService("src/database/Proposal.xml"); // Adjust path as needed
    }

    @FXML
    private void handleSubmitProposal(ActionEvent event) {
        String title = titleField.getText();
        LocalDate date = datePicker.getValue();
        String description = descriptionArea.getText();
        String location = locationField.getText();

        if (title.isEmpty() || date == null || description.isEmpty() || location.isEmpty()) {
            // Show error message for empty fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled out.");
            alert.showAndWait();
            return;
        }

        String status = "Pending"; // Set default status

        ProposalForm proposalForm = new ProposalForm(title, date, location, description, status);
        proposalFormService.saveProposal(proposalForm);

        // Show success message using Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Proposal submitted successfully!");
        alert.showAndWait();

        // Clear form fields
        titleField.clear();
        datePicker.setValue(null);
        locationField.clear();
        descriptionArea.clear();
    }
}
