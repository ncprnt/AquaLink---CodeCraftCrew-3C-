package userverification;

import javafx.scene.layout.HBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class UserVerification {
    private final String firstName;
    private final String lastName;
    private final String dob;
    private final String email;
    private final String role;
    private String status; // Change status to be mutable
    private final HBox actions;

    public UserVerification(String firstName, String lastName, String dob, String email, String role, String status, HBox actions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.role = role;
        this.status = status;
        this.actions = actions;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HBox getActions() {
        return actions;
    }

    public void updateStatusInXML(String newStatus) {
        try {
            // Load XML file
            File xmlFile = new File("src/database/Account.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Find the corresponding <user> element in XML and update status
            NodeList nodeList = doc.getElementsByTagName("user");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String userEmail = element.getElementsByTagName("email").item(0).getTextContent();
                if (userEmail.equals(this.getEmail())) {
                    element.getElementsByTagName("status").item(0).setTextContent(newStatus);
                    break;
                }
            }

            // Save changes back to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
