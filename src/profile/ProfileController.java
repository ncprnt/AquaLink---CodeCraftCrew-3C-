package profile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private File xmlFile;

    private String currentUserEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        xmlFile = new File("src/database/Akun.xml"); // Adjust path as needed
        loadUserProfile();
    }

    private void loadUserProfile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            Element userElement = getUserElementByEmail(doc, currentUserEmail);

            if (userElement != null) {
                String firstName = getElementValue(userElement, "firstName", "John");
                String lastName = getElementValue(userElement, "lastName", "Doe");
                LocalDate dob = LocalDate.parse(getElementValue(userElement, "dob", "2024-01-01"));
                String email = getElementValue(userElement, "email", "");
                String password = getElementValue(userElement, "password", "");

                firstNameField.setText(firstName);
                lastNameField.setText(lastName);
                dobField.setValue(dob);
                emailField.setText(email);
                passwordField.setText(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getElementValue(Element parentElement, String tagName, String defaultValue) {
        Element element = (Element) parentElement.getElementsByTagName(tagName).item(0);
        return element != null ? element.getTextContent() : defaultValue;
    }

    private Element getUserElementByEmail(Document doc, String email) {
        NodeList userElements = doc.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < userElements.getLength(); i++) {
            Node userNode = userElements.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                if (userElement.getElementsByTagName("email").item(0).getTextContent().equals(email)) {
                    return userElement;
                }
            }
        }
        return null;
    }

    @FXML
    private void handleUpdateProfile() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate dob = dobField.getValue();
            String email = emailField.getText();
            String password = passwordField.getText();

            updateUserInXML(firstName, lastName, dob.toString(), email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserInXML(String firstName, String lastName, String dob, String email, String password)
            throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        Element userElement = getUserElementByEmail(doc, currentUserEmail);

        if (userElement != null) {
            updateElementValue(userElement, "firstName", firstName);
            updateElementValue(userElement, "lastName", lastName);
            updateElementValue(userElement, "dob", dob);
            updateElementValue(userElement, "email", email);
            updateElementValue(userElement, "password", password);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        }
    }

    private void updateElementValue(Element parentElement, String tagName, String newValue) {
        Element element = (Element) parentElement.getElementsByTagName(tagName).item(0);
        if (element != null) {
            element.setTextContent(newValue);
        }
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }
}
