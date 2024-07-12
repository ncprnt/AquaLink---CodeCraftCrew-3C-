package article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class ArticleController {

    @FXML
    private Pane contentPane;

    @FXML
    private Label articleTitle;

    @FXML
    private ImageView articlePicture;

    @FXML
    private Label upperParagraph;

    @FXML
    private Label lowerParagraph;

    private int currentArticleIndex; // To store the current article index

    @FXML
    public void initialize() {
        loadArticle(currentArticleIndex);
        loadArticles();
    }

    private void loadArticles() {
        try {
            File file = new File("src/database/Article.xml");

            if (!file.exists()) {
                showAlert("Error", "No articles found!", Alert.AlertType.ERROR);
                return;
            }

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element root = document.getDocumentElement();

            NodeList articleNodes = root.getElementsByTagName("article");

            if (articleNodes.getLength() == 0) {
                showAlert("Error", "No articles found!", Alert.AlertType.ERROR);
                return;
            }

            // Assuming you load the first article initially
            loadArticle(0);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load articles!", Alert.AlertType.ERROR);
        }
    }

    // Method to load a specific article based on index
    public void loadArticle(int index) {
        try {
            File file = new File("src/database/Article.xml");

            if (!file.exists()) {
                showAlert("Error", "No articles found!", Alert.AlertType.ERROR);
                return;
            }

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Element root = document.getDocumentElement();

            NodeList articleNodes = root.getElementsByTagName("article");

            if (index < 0 || index >= articleNodes.getLength()) {
                showAlert("Error", "Invalid article index!", Alert.AlertType.ERROR);
                return;
            }

            Element articleElement = (Element) articleNodes.item(index);

            String title = articleElement.getElementsByTagName("title").item(0).getTextContent();
            String imagePath = articleElement.getElementsByTagName("imagePath").item(0).getTextContent();
            String upperParagraphContent = articleElement.getElementsByTagName("upperParagraph").item(0).getTextContent();
            String lowerParagraphContent = articleElement.getElementsByTagName("lowerParagraph").item(0).getTextContent();

            articleTitle.setText(title);
            articlePicture.setImage(new Image(new File(imagePath).toURI().toString()));
            upperParagraph.setText(upperParagraphContent);
            lowerParagraph.setText(lowerParagraphContent);

            // Set the current article index
            currentArticleIndex = index;

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load article!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleLoadBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publics/Public.fxml"));
            Parent signUpPage = loader.load();

            Scene signUpScene = new Scene(signUpPage);
            Stage currentStage = (Stage) contentPane.getScene().getWindow();

            currentStage.setScene(signUpScene);
            currentStage.setTitle("Sign Up");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to handle clicking "Read More" button
    @FXML
    private void handleReadMore() {
        // Load the next article, assuming we have a list of articles
        loadArticle(currentArticleIndex + 1); // Load the next article
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
