package fundraise;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
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
import java.time.LocalDate;

public class FundraiseController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private TextArea descriptionArea;

    private static final String XML_FILE_PATH = "src/database/Fundraise.xml";

    @FXML
    private void handlePublishFundraise(ActionEvent event) {
        String title = titleField.getText();
        LocalDate deadline = deadlinePicker.getValue();
        String description = descriptionArea.getText();

        if (title.isEmpty() || deadline == null || description.isEmpty()) {
            // Show error message or validation
            return;
        }

        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("fundraises");
                doc.appendChild(rootElement);
            }

            Element rootElement = doc.getDocumentElement();
            int newId = rootElement.getElementsByTagName("fundraise").getLength() + 1;

            Element fundraiseElement = doc.createElement("fundraise");
            rootElement.appendChild(fundraiseElement);

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            fundraiseElement.appendChild(idElement);

            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode(title));
            fundraiseElement.appendChild(titleElement);

            Element deadlineElement = doc.createElement("deadline");
            deadlineElement.appendChild(doc.createTextNode(deadline.toString()));
            fundraiseElement.appendChild(deadlineElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(description));
            fundraiseElement.appendChild(descriptionElement);

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
            alert.setContentText("Fundraiser published successfully!");
            alert.showAndWait();

            // Optionally handle navigation or clear form fields
            clearFormFields();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (show error message, log, etc.)
        }
    }

    // Method to clear the form fields
    private void clearFormFields() {
        titleField.clear();
        deadlinePicker.setValue(null);
        descriptionArea.clear();
    }
}
