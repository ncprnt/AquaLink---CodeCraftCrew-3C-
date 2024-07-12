package article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

public class ArticleFormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea upperParagraphField;

    @FXML
    private TextArea lowerParagraphField;

    @FXML
    private Button imageButton;

    private File selectedImageFile;

    @FXML
    private void handleChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) imageButton.getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);

        if (selectedImageFile != null) {
            imageButton.setText(selectedImageFile.getName());
        }
    }

    @FXML
    private void handleSubmitArticle(ActionEvent event) {
        String title = titleField.getText();
        String upperParagraph = upperParagraphField.getText();
        String lowerParagraph = lowerParagraphField.getText();

        if (title.isEmpty() || upperParagraph.isEmpty() || lowerParagraph.isEmpty() || selectedImageFile == null) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (wordCount(title) >= 10) {
            showAlert("Error", "Title must be less than 10 words!", Alert.AlertType.ERROR);
            return;
        }

        if (wordCount(upperParagraph) >= 150) {
            showAlert("Error", "Upper paragraph must be less than 150 words!", Alert.AlertType.ERROR);
            return;
        }

        if (wordCount(lowerParagraph) >= 400) {
            showAlert("Error", "Lower paragraph must be less than 400 words!", Alert.AlertType.ERROR);
            return;
        }

        ArticleForm article = new ArticleForm(title, selectedImageFile.getAbsolutePath(), upperParagraph, lowerParagraph);
        saveArticleToXML(article);

        titleField.clear();
        upperParagraphField.clear();
        lowerParagraphField.clear();
        imageButton.setText("Choose Image");
        selectedImageFile = null;

        showAlert("Success", "Article submitted successfully!", Alert.AlertType.INFORMATION);
    }

    private int wordCount(String text) {
        return text.split("\\s+").length;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveArticleToXML(ArticleForm article) {
        try {
            System.out.println(article.getImagePath());
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document;

            File file = new File("src/database/Article.xml");
            Element root;

            if (file.exists()) {
                document = documentBuilder.parse(file);
                root = document.getDocumentElement();
            } else {
                document = documentBuilder.newDocument();
                root = document.createElement("articles");
                document.appendChild(root);
            }

            Element articleElement = document.createElement("article");

            Element titleElement = document.createElement("title");
            titleElement.appendChild(document.createTextNode(article.getTitle()));
            articleElement.appendChild(titleElement);

            Element imagePathElement = document.createElement("imagePath");
            imagePathElement.appendChild(document.createTextNode(article.getImagePath()));
            articleElement.appendChild(imagePathElement);

            Element upperParagraphElement = document.createElement("upperParagraph");
            upperParagraphElement.appendChild(document.createTextNode(article.getUpperParagraph()));
            articleElement.appendChild(upperParagraphElement);

            Element lowerParagraphElement = document.createElement("lowerParagraph");
            lowerParagraphElement.appendChild(document.createTextNode(article.getLowerParagraph()));
            articleElement.appendChild(lowerParagraphElement);

            root.appendChild(articleElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save the article!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }
    }
}
