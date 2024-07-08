package fundraise;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DonationTableViewController {

    @FXML
    private TableView<JoinFundraise> donationTable;

    @FXML
    private TableColumn<JoinFundraise, Integer> colDonationId;

    @FXML
    private TableColumn<JoinFundraise, String> colDonationProjectTitle; // Added project title column

    @FXML
    private TableColumn<JoinFundraise, String> colDonationFullName;

    @FXML
    private TableColumn<JoinFundraise, Double> colDonationAmount;

    private static final String XML_FILE_PATH = "src/database/Donation.xml";

    @FXML
    public void initialize() {
        colDonationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDonationProjectTitle.setCellValueFactory(new PropertyValueFactory<>("projectTitle")); // Added project title column
        colDonationFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDonationAmount.setCellValueFactory(new PropertyValueFactory<>("donationAmount"));

        // Load the donation data from XML
        loadDonationsFromXML();
    }

    private void loadDonationsFromXML() {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
    
            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
    
                NodeList nodeList = doc.getElementsByTagName("donation");
                List<JoinFundraise> donations = new ArrayList<>();
    
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    String projectTitle = element.getElementsByTagName("projectTitle").item(0).getTextContent();
                    String fullName = element.getElementsByTagName("fullName").item(0).getTextContent();
                    double donationAmount = Double.parseDouble(element.getElementsByTagName("amount").item(0).getTextContent());
    
                    JoinFundraise donation = new JoinFundraise(id, projectTitle, fullName, donationAmount);
                    donations.add(donation);
                }
    
                donationTable.getItems().setAll(donations);
            } else {
                System.err.println("XML file not found: " + XML_FILE_PATH);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
