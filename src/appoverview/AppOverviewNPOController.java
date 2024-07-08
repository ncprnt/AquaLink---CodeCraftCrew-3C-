package appoverview;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppOverviewNPOController {
        @FXML
    private Pane paneBackground;

    @FXML
    private void handleLoadBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/npo/NPO.fxml"));
            Parent signUpPage = loader.load();

            Scene signUpScene = new Scene(signUpPage);
            Stage currentStage = (Stage) paneBackground.getScene().getWindow();

            currentStage.setScene(signUpScene);
            currentStage.setTitle("AquaLink");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
