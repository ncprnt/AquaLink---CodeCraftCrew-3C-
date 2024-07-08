package userverification;

import javafx.scene.layout.HBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserVerificationService {

    public List<UserVerification> loadUsers() {
        List<UserVerification> userList = new ArrayList<>();

        try {
            File xmlFile = new File("src/database/Account.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String dob = element.getElementsByTagName("dob").item(0).getTextContent();
                String email = element.getElementsByTagName("email").item(0).getTextContent();
                String role = element.getElementsByTagName("role").item(0).getTextContent();
                String status = element.getElementsByTagName("status").item(0).getTextContent();

                HBox actions = new HBox();

                UserVerification user = new UserVerification(firstName, lastName, dob, email, role, status, actions);
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
}
