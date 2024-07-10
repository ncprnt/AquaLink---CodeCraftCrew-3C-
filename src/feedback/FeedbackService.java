package feedback;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService {

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
}
