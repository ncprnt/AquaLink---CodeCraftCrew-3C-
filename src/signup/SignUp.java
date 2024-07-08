package signup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SignUp {

    private static final String XML_FILE_PATH = "src/database/Account.xml";

    public static void saveUser(String firstName, String lastName, String dob, String email, String password, String role)
            throws ParserConfigurationException, IOException {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
            Element rootElement;

            if (xmlFile.exists()) {
                if (xmlFile.length() == 0) {
                    doc = dBuilder.newDocument();
                    rootElement = doc.createElement("users");
                    doc.appendChild(rootElement);
                } else {
                    doc = dBuilder.parse(xmlFile);
                    rootElement = doc.getDocumentElement();
                }
            } else {
                doc = dBuilder.newDocument();
                rootElement = doc.createElement("users");
                doc.appendChild(rootElement);
            }

            Element userElement = doc.createElement("user");
            rootElement.appendChild(userElement);

            Element firstNameElement = doc.createElement("firstName");
            firstNameElement.appendChild(doc.createTextNode(firstName));
            userElement.appendChild(firstNameElement);

            Element lastNameElement = doc.createElement("lastName");
            lastNameElement.appendChild(doc.createTextNode(lastName));
            userElement.appendChild(lastNameElement);

            Element dobElement = doc.createElement("dob");
            dobElement.appendChild(doc.createTextNode(dob));
            userElement.appendChild(dobElement);

            Element emailElement = doc.createElement("email");
            emailElement.appendChild(doc.createTextNode(email));
            userElement.appendChild(emailElement);

            Element passwordElement = doc.createElement("password");
            passwordElement.appendChild(doc.createTextNode(password));
            userElement.appendChild(passwordElement);

            Element roleElement = doc.createElement("role");
            roleElement.appendChild(doc.createTextNode(role));
            userElement.appendChild(roleElement);

            // Add status element with default value "Pending"
            Element statusElement = doc.createElement("status");
            statusElement.appendChild(doc.createTextNode("Pending"));
            userElement.appendChild(statusElement);

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no"); // Set indentation to "no"
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no"); // Preserve XML declaration
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error saving user data.");
        }
    }
}
