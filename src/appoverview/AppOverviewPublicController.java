package appoverview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;

public class AppOverviewPublicController {

    @FXML
    private Pane paneBackground;

    @FXML
    private void handleLoadBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publics/Public.fxml"));
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
