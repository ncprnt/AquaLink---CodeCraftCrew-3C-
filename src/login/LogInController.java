package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import npo.NPO;
import publics.Public;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import administrator.Administrator;
import government.Government;


public class LogInController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private Pane contentPane;

    private AuthenticationService authService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        authService = new AuthenticationService("src/database/Account.xml");
    }

    @FXML
    private void handleLogInButtonClick(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            if (!termsCheckBox.isSelected()) {
                showAlert("Please agree to the Terms of Service.");
                return;
            }

            if (validateLoginInput(email, password)) {
                String role = authService.authenticateUser(email, password);
                if (role != null) {
                    showAlert("Login successful!");
                    navigateToRolePage(email, role);
                } else {
                    showAlert("User not found or your account is not approved yet.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("An error occurred. Please try again.");
        }
    }

    private boolean validateLoginInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Please enter email and password.");
            return false;
        }
        return true;
    }

    private void navigateToRolePage(String email, String role) {
        String fxmlPath = "";
        switch (role) {
            case "Administrator":
                fxmlPath = "/administrator/Administrator.fxml";
                break;
            case "Public":
                fxmlPath = "/publics/Public.fxml";
                break;
            case "Government":
                fxmlPath = "/government/Government.fxml";
                break;
            case "NPO":
                fxmlPath = "/npo/NPO.fxml";
                break;
            default:
                showAlert("Unknown role: " + role);
                return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the controller of the next scene and pass the email
            Object controller = loader.getController();

            if (controller instanceof Public) {
                Public publicController = (Public) controller;
                publicController.setLoggedInUser(email);
                publicController.initializeWithUser();
            } else if (controller instanceof NPO) {
                NPO npoController = (NPO) controller;
                npoController.setLoggedInUser(email);
                npoController.initializeWithUser();
            } else if (controller instanceof Government) {
                Government governmentController = (Government) controller;
                governmentController.setLoggedInUser(email);
                governmentController.initializeWithUser();
            } else if (controller instanceof Administrator) {
                Administrator npoController = (Administrator) controller;
                npoController.setLoggedInUser(email);
                npoController.initializeWithUser();
            } else {
                showAlert("Unknown controller type: " + controller.getClass().getSimpleName());
                return;
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(role + " Dashboard");
            stage.show();

            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load dashboard.");
        }
    }

    @FXML
    private void handleSignUpButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup/SignUp.fxml"));
            Parent signUpPage = loader.load();

            Scene signUpScene = new Scene(signUpPage);
            Stage currentStage = (Stage) emailField.getScene().getWindow();

            currentStage.setScene(signUpScene);
            currentStage.setTitle("Sign Up");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load Sign Up page.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
