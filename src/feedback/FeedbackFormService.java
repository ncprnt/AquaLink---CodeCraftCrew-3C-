package feedback;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeedbackFormService {

    public List<Feedback> loadFeedbackFromXML() {
        List<Feedback> feedbacks = new ArrayList<>();

        try {
            File xmlFile = new File("src/database/Feedback.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList feedbackList = doc.getElementsByTagName("feedback");

            for (int i = 0; i < feedbackList.getLength(); i++) {
                Element feedbackElement = (Element) feedbackList.item(i);

                int id = Integer.parseInt(feedbackElement.getElementsByTagName("id").item(0).getTextContent());
                String fullName = feedbackElement.getElementsByTagName("fullName").item(0).getTextContent();
                String projectName = feedbackElement.getElementsByTagName("projectName").item(0).getTextContent();
                String dateStr = feedbackElement.getElementsByTagName("date").item(0).getTextContent();
                LocalDate date = LocalDate.parse(dateStr);
                String location = feedbackElement.getElementsByTagName("location").item(0).getTextContent();
                String description = feedbackElement.getElementsByTagName("description").item(0).getTextContent();

                Feedback feedback = new Feedback(id, fullName, projectName, date, location, description);
                feedbacks.add(feedback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feedbacks;
    }

    public void saveFeedbackToXML(Feedback feedback) {
        try {
            File xmlFile = new File("src/database/Feedback.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
            } else {
                // Create new document if Feedback.xml doesn't exist
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("feedbacks");
                doc.appendChild(rootElement);
            }

            // Find the root element
            Element rootElement = doc.getDocumentElement();

            // Generate new feedback ID
            NodeList feedbackList = rootElement.getElementsByTagName("feedback");
            int newId = feedbackList.getLength() + 1;
            feedback.setId(newId);

            // Create new feedback element
            Element newFeedback = doc.createElement("feedback");

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(feedback.getId())));
            newFeedback.appendChild(idElement);

            Element fullNameElement = doc.createElement("fullName");
            fullNameElement.appendChild(doc.createTextNode(feedback.getFullName()));
            newFeedback.appendChild(fullNameElement);

            Element projectNameElement = doc.createElement("projectName");
            projectNameElement.appendChild(doc.createTextNode(feedback.getProjectName()));
            newFeedback.appendChild(projectNameElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(feedback.getDate().toString()));
            newFeedback.appendChild(dateElement);

            Element locationElement = doc.createElement("location");
            locationElement.appendChild(doc.createTextNode(feedback.getLocation()));
            newFeedback.appendChild(locationElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(feedback.getDescription()));
            newFeedback.appendChild(descriptionElement);

            rootElement.appendChild(newFeedback);

            // Save the updated document
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
