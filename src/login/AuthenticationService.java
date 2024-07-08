package login;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class AuthenticationService {

    private File xmlFile;

    public AuthenticationService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public String authenticateUser(String email, String password) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String storedEmail = element.getElementsByTagName("email").item(0).getTextContent();
                    String storedPassword = element.getElementsByTagName("password").item(0).getTextContent();
                    String role = element.getElementsByTagName("role").item(0).getTextContent();
                    String status = element.getElementsByTagName("status").item(0).getTextContent();

                    if (storedEmail.equals(email) && storedPassword.equals(password)) {
                        if (status.equals("Approved")) {
                            return role;
                        } else {
                            return null; // User status is not Approved
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // User not found or credentials do not match
    }
}
