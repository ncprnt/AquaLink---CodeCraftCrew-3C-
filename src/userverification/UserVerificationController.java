package userverification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UserVerificationController implements Initializable {

    @FXML
    private TableView<UserVerification> tableUserVerification;
    @FXML
    private TableColumn<UserVerification, String> firstNameColumn;
    @FXML
    private TableColumn<UserVerification, String> lastNameColumn;
    @FXML
    private TableColumn<UserVerification, String> dobColumn;
    @FXML
    private TableColumn<UserVerification, String> emailColumn;
    @FXML
    private TableColumn<UserVerification, String> roleColumn;
    @FXML
    private TableColumn<UserVerification, String> statusColumn;
    @FXML
    private TableColumn<UserVerification, HBox> actionColumn;

    private ObservableList<UserVerification> userData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userData = FXCollections.observableArrayList();
        loadUsers();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

        tableUserVerification.setItems(userData);
    }

    private void loadUsers() {
        UserVerificationService service = new UserVerificationService();
        userData.addAll(service.loadUsers());

        for (UserVerification user : userData) {
            ComboBox<String> statusComboBox = new ComboBox<>();
            statusComboBox.getItems().addAll("Pending", "Approved", "Rejected");
            statusComboBox.setValue(user.getStatus());
            statusComboBox.setStyle("-fx-border-color: #c9c9c9; -fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");
            statusComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                user.setStatus(newVal);
                user.updateStatusInXML(newVal);
                tableUserVerification.refresh();
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: red; -fx-border-radius: 10; -fx-background-radius: 10;");
            deleteButton.setOnAction(event -> userData.remove(user));

            HBox actions = new HBox(10, statusComboBox, deleteButton);
            user.getActions().getChildren().setAll(actions.getChildren());
        }
    }
}
