package publics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import article.ArticleController;

public class Public implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private BarChart<String, Number> projectChart;

    @FXML
    private Pane mainContentPane;

    @FXML
    private AnchorPane loadedArticle;

    private String loggedInUserEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProjectData();
        loadArticles();
        projectChart.setBarGap(20);
        projectChart.setCategoryGap(20);
    }

    public void setLoggedInUser(String email) {
        this.loggedInUserEmail = email;
    }

    public void initializeWithUser() {
        loadUserName();
    }

    private void loadUserName() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/database/Account.xml");

            NodeList accounts = doc.getElementsByTagName("user");

            for (int i = 0; i < accounts.getLength(); i++) {
                Element account = (Element) accounts.item(i);
                String email = account.getElementsByTagName("email").item(0).getTextContent();

                if (email.equals(loggedInUserEmail)) {
                    String firstName = account.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = account.getElementsByTagName("lastName").item(0).getTextContent();
                    String fullName = firstName + " " + lastName + "!";
                    setNameLabel(fullName);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    private void loadProjectData() {
        int pendingCount = 0;
        int onGoingCount = 0;
        int completedCount = 0;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/database/Project.xml");

            NodeList projects = doc.getElementsByTagName("project");

            for (int i = 0; i < projects.getLength(); i++) {
                Element project = (Element) projects.item(i);
                String status = project.getElementsByTagName("status").item(0).getTextContent();

                switch (status) {
                    case "Pending":
                        pendingCount++;
                        break;
                    case "On Going":
                        onGoingCount++;
                        break;
                    case "Completed":
                        completedCount++;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Pending", pendingCount));
        series.getData().add(new XYChart.Data<>("On Going", onGoingCount));
        series.getData().add(new XYChart.Data<>("Completed", completedCount));

        projectChart.getData().add(series);

        // Apply CSS class to series for styling
        for (int i = 0; i < series.getData().size(); i++) {
            series.getData().get(i).getNode().getStyleClass().add("series" + (i + 6)); // Offset by 6 for project series
        }
    }

    @FXML
    private void handleLoadDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publics/Public.fxml"));
            Parent publicPage = loader.load();

            Scene dashboard = new Scene(publicPage);
            Stage currentStage = (Stage) mainContentPane.getScene().getWindow();

            currentStage.setScene(dashboard);
            currentStage.setTitle("Public Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoadOverview(ActionEvent event) {
        loadPane("/appoverview/AppOverviewPublic.fxml");
    }

    @FXML
    private void handleSubmitReport(ActionEvent event) {
        loadPane("/report/ReportForm.fxml");
    }

    @FXML
    private void handleLoadReports(ActionEvent event) {
        loadPane("/report/Report.fxml");
    }

    @FXML
    private void handleLoadProjects(ActionEvent event) {
        loadPane("/project/Project.fxml");
    }

    @FXML
    private void handleLoadFeedback(ActionEvent event) {
        loadPane("/feedback/FeedbackForm.fxml");
    }

    @FXML
    private void handleLoadDonate(ActionEvent event) {
        loadPane("/fundraise/JoinFundraise.fxml");
    }

    @FXML
    private void handleLoadFundraise(ActionEvent event) {
        loadPane("/fundraise/FundraiseTableView.fxml");
    }

    private void loadPane(String fxmlPath) {
        try {
            System.out.println("Loading FXML: " + fxmlPath);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent pane = loader.load();
            mainContentPane.getChildren().clear(); // Clear existing children
            mainContentPane.getChildren().add(pane); // Add the new pane
            System.out.println("Successfully loaded: " + fxmlPath);
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup/SignUp.fxml"));
            Parent signUpPage = loader.load();

            Scene signUpScene = new Scene(signUpPage);
            Stage currentStage = (Stage) mainContentPane.getScene().getWindow();

            currentStage.setScene(signUpScene);
            currentStage.setTitle("Sign Up");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadArticles() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("src/database/Article.xml"));
    
            NodeList articles = doc.getElementsByTagName("article");
    
            // Load up to three articles dynamically
            for (int i = 0; i < Math.min(articles.getLength(), 3); i++) {
                Element article = (Element) articles.item(i);
                String title = article.getElementsByTagName("title").item(0).getTextContent();
                String imagePath = article.getElementsByTagName("imagePath").item(0).getTextContent();
    
                // Create a new Pane for each article
                Pane articlePane = new Pane();
                articlePane.setLayoutX(7.0 + i * 217.0); // Adjust the layoutX position, reducing spacing to 220.0
                articlePane.setLayoutY(15.0);
                articlePane.setPrefSize(204.0, 270.0);
                articlePane.getStyleClass().add("pane-2"); // Add style class if needed
                articlePane.getStylesheets().add("publics/Public.css"); // Add stylesheet if needed
    
                // Create Label for the article title
                Label articleLabel = new Label();
                articleLabel.setLayoutX(22.0);
                articleLabel.setLayoutY(135.0);
                articleLabel.setPrefWidth(160.0);
                articleLabel.setPrefHeight(70.0);
                articleLabel.setText(title);
                articleLabel.setTextFill(javafx.scene.paint.Color.WHITE);
                articleLabel.setWrapText(true);
                articleLabel.setFont(new javafx.scene.text.Font("System Bold", 12.0));
    
                // Create ImageView for the article image
                ImageView imageView = new ImageView();
                Image image = new Image(new File(imagePath).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(160); // Set image width
                imageView.setFitHeight(106.4); // Set image height
                imageView.setLayoutX(22);
                imageView.setLayoutY(28);
    
                // Create Button for "Read More"
                final Integer index = i;
                Button btnArticle = new Button();
                btnArticle.setLayoutX(61.0);
                btnArticle.setLayoutY(223.0);
                btnArticle.setPrefWidth(82.0);
                btnArticle.setPrefHeight(27.0);
                btnArticle.setText("Read More");
                btnArticle.setTextFill(javafx.scene.paint.Color.WHITE);
                btnArticle.setOnAction(event -> handleLoadArticle(index)); // Load article based on index
                btnArticle.getStyleClass().add("button-read-more"); // Add style class if needed
                btnArticle.getStylesheets().add("publics/Public.css"); // Add stylesheet if needed
    
                btnArticle.setStyle("-fx-font-weight: bold;");
    
                // Add nodes to the article Pane
                articlePane.getChildren().addAll(articleLabel, imageView, btnArticle);
    
                // Add the article Pane to the loadedArticle AnchorPane
                loadedArticle.getChildren().add(articlePane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
private void handleLoadArticle(int articleIndex) {
    try {
        String fxmlPath = "/article/Article.fxml"; // Path to your article FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent articlePage = loader.load();

        // Pass the article index to the controller
        ArticleController controller = loader.getController();
        controller.loadArticle(articleIndex);

        // Assuming mainContentPane is where you want to load the article
        mainContentPane.getChildren().clear(); // Clear existing content
        mainContentPane.getChildren().add(articlePage); // Load new article content

        // Optionally, you can set a title or do other setup for the loaded article
        Stage currentStage = (Stage) mainContentPane.getScene().getWindow();
        currentStage.setTitle("Article Page");

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    

}
