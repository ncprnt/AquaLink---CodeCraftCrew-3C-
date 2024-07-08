package signup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> roleComboBox;

    private String[] roles = {"Public", "NPO", "Government", "Administrator"};

    @FXML
    private Pane contentPane;

    @FXML
    private CheckBox termsCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleComboBox.getItems().addAll(roles);
    }

    @FXML
    private void handleSignUpButtonClick(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate dob = dobField.getValue();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Retrieve the selected role from combo box
        String selectedRole = roleComboBox.getValue();

        // Check if terms of service checkbox is selected
        if (!termsCheckBox.isSelected()) {
            showAlert("Please agree to the Terms of Service.");
            return;
        }

        try {
            if (validateSignUpInput(firstName, lastName, dob, email, password, confirmPassword, selectedRole)) {
                SignUp.saveUser(firstName, lastName, dob.toString(), email, password, selectedRole);
                showAlert("Sign up successful! Please wait for the Administrator to verify your data");

                // Navigate to the login page
                loadFXML("/login/LogIn.fxml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("An error occurred. Please try again.");
        }
    }

    private boolean validateSignUpInput(String firstName, String lastName, LocalDate dob, String email, String password, String confirmPassword, String role) {
        if (firstName.isEmpty() || lastName.isEmpty() || dob == null || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            showAlert("Please fill out all fields and select a role.");
            return false;
        }
        if (!email.endsWith("@gmail.com") && !email.endsWith("@mail.go.id")) {
            showAlert("Email must be a valid email");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match.");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleLogInButtonClick(ActionEvent event) {
        loadFXML("/login/LogIn.fxml");
    }

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent loadedPane = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(loadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
