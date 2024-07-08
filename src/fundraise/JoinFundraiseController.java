package fundraise;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

public class JoinFundraiseController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField donationAmountField;

    @FXML
    private TextField projectNameField;

    private static final String XML_FILE_PATH = "src/database/Donation.xml";

    @FXML
    private void handleJoinFundraise(ActionEvent event) {
        String fullName = fullNameField.getText();
        String donationAmountText = donationAmountField.getText();
        String projectName = projectNameField.getText();

        if (fullName.isEmpty() || donationAmountText.isEmpty() || projectName.isEmpty()) {
            // Show error message or validation
            return;
        }

        try {
            double donationAmount = Double.parseDouble(donationAmountText);
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("donations");
                doc.appendChild(rootElement);
            }

            Element rootElement = doc.getDocumentElement();
            int newId = rootElement.getElementsByTagName("donation").getLength() + 1;

            Element donationElement = doc.createElement("donation");
            rootElement.appendChild(donationElement);

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            donationElement.appendChild(idElement);

            Element projectTitleElement = doc.createElement("projectTitle");
            projectTitleElement.appendChild(doc.createTextNode(projectName));
            donationElement.appendChild(projectTitleElement);

            Element fullNameElement = doc.createElement("fullName");
            fullNameElement.appendChild(doc.createTextNode(fullName));
            donationElement.appendChild(fullNameElement);

            Element amountElement = doc.createElement("amount");
            amountElement.appendChild(doc.createTextNode(String.valueOf(donationAmount)));
            donationElement.appendChild(amountElement);

            // Write the updated XML file without indentation
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no"); // Set indentation to "no"
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no"); // Preserve XML declaration
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            // Show success message using Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Donation recorded successfully!");
            alert.showAndWait();

            // Clear the form fields
            clearFormFields();

        } catch (NumberFormatException e) {
            // Handle if donation amount is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid donation amount.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions (show error message, log, etc.)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error recording donation. Please try again.");
            alert.showAndWait();
        }
    }

    // Method to clear the form fields
    private void clearFormFields() {
        fullNameField.clear();
        donationAmountField.clear();
        projectNameField.clear();
    }
}
