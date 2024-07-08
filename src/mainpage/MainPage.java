package mainpage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPage {
    
    @FXML
    private void handleJoinUsButtonAction(ActionEvent event) {
        try {
            Parent accessPage = FXMLLoader.load(getClass().getResource("/signup/SignUp.fxml"));
            Scene accessScene = new Scene(accessPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(accessScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
