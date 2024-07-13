package npo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NPO implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private BarChart<String, Number> projectChart;

    private String loggedInUserEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProjectData();
        loadUserName();

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
    private Pane mainContentPane;

    @FXML
    private void handleLoadOverview(ActionEvent event) {
        loadFXML("/appoverview/AppOverviewNPO.fxml");
    }

    @FXML
    private void handleSubmitProposal() {
        loadFXML("/proposal/ProposalForm.fxml");
    }

    @FXML
    private void handleLoadProposal() {
        loadFXML("/proposal/Proposal.fxml");
    }

    @FXML
    private void handleSubmitProgress() {
        loadFXML("/progress/ProgressForm.fxml");
    }

    @FXML
    private void handleSubmitProject() {
        loadFXML("/project/ProjectForm.fxml");
    }

    @FXML
    private void handleLoadProjects() {
        loadFXML("/project/Project.fxml");
    }

    @FXML
    private void handleLoadReports() {
        loadFXML("/report/Report.fxml");
    }

    @FXML
    private void handleLoadFundraise() {
        loadFXML("/fundraise/Fundraise.fxml");
    }

    @FXML
    private void handleLoadFundList() {
        loadFXML("/fundraise/DonationTableView.fxml");
    }

    private void loadFXML(String fxmlFile) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlFile));
            mainContentPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
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

    @FXML
    private void handleLoadDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/npo/NPO.fxml"));
            Parent publicPage = loader.load();

            Scene dashboard = new Scene(publicPage);
            Stage currentStage = (Stage) mainContentPane.getScene().getWindow();

            currentStage.setScene(dashboard);
            currentStage.setTitle("NPO Dashboard");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
