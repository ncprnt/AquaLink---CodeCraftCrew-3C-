package progress;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProgressService {

    private File xmlFile;

    public ProgressService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public List<Progress> loadReportsFromXML() {
        List<Progress> reports = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("progress");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                String projectName = element.getElementsByTagName("projectName").item(0).getTextContent();
                String date = element.getElementsByTagName("date").item(0).getTextContent();
                String location = element.getElementsByTagName("location").item(0).getTextContent();
                String description = element.getElementsByTagName("description").item(0).getTextContent();

                Progress report = new Progress(id, projectName, date, location, description);
                reports.add(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle specific exceptions as needed
        }
        return reports;
    }
}
