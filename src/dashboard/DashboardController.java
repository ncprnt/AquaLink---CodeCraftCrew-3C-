package dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> reportChart;

    @FXML
    private BarChart<String, Number> proposalChart;

    @FXML
    private BarChart<String, Number> projectChart;

    @FXML
    private Label reportLabel;

    @FXML
    private Label proposalLabel;

    @FXML
    private Label projectLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadReportData();
        loadProposalData();
        loadProjectData();

        // Adjust bar gap and category gap for reportChart
        reportChart.setBarGap(20);
        reportChart.setCategoryGap(20);

        // Adjust bar gap and category gap for proposalChart
        proposalChart.setBarGap(20);
        proposalChart.setCategoryGap(20);

        // Adjust bar gap and category gap for projectChart
        projectChart.setBarGap(20);
        projectChart.setCategoryGap(20);
    }

    private void loadReportData() {
        int pendingCount = 0;
        int rejectedCount = 0;
        int acceptedCount = 0;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/database/Report.xml");

            NodeList reports = doc.getElementsByTagName("report");

            for (int i = 0; i < reports.getLength(); i++) {
                Element report = (Element) reports.item(i);
                String status = report.getElementsByTagName("status").item(0).getTextContent();

                switch (status) {
                    case "Pending":
                        pendingCount++;
                        break;
                    case "Rejected":
                        rejectedCount++;
                        break;
                    case "Accepted":
                        acceptedCount++;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Pending", pendingCount));
        series.getData().add(new XYChart.Data<>("Rejected", rejectedCount));
        series.getData().add(new XYChart.Data<>("Accepted", acceptedCount));

        reportChart.getData().add(series);

        // Apply CSS class to series for styling
        for (int i = 0; i < series.getData().size(); i++) {
            series.getData().get(i).getNode().getStyleClass().add("series" + i);
        }

        reportLabel.setText(String.valueOf(acceptedCount));
    }

    private void loadProposalData() {
        int pendingCount = 0;
        int rejectedCount = 0;
        int acceptedCount = 0;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/database/Proposal.xml");

            NodeList proposals = doc.getElementsByTagName("proposal");

            for (int i = 0; i < proposals.getLength(); i++) {
                Element proposal = (Element) proposals.item(i);
                String status = proposal.getElementsByTagName("status").item(0).getTextContent();

                switch (status) {
                    case "Pending":
                        pendingCount++;
                        break;
                    case "Rejected":
                        rejectedCount++;
                        break;
                    case "Accepted":
                        acceptedCount++;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Pending", pendingCount));
        series.getData().add(new XYChart.Data<>("Rejected", rejectedCount));
        series.getData().add(new XYChart.Data<>("Accepted", acceptedCount));

        proposalChart.getData().add(series);

        // Apply CSS class to series for styling
        for (int i = 0; i < series.getData().size(); i++) {
            series.getData().get(i).getNode().getStyleClass().add("series" + (i + 3)); // Offset by 3 for proposal series
        }

        proposalLabel.setText(String.valueOf(acceptedCount));
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

        projectLabel.setText(String.valueOf(completedCount));
    }
}
