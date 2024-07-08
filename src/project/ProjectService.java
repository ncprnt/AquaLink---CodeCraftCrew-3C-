package project;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    private File xmlFile;

    public ProjectService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public List<Project> loadProjectsFromXML() {
        List<Project> projects = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            if (doc == null) {
                System.out.println("Document is null after parsing.");
                return projects; // or handle accordingly
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("project");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                String projectName = element.getElementsByTagName("projectName").item(0).getTextContent();
                String date = element.getElementsByTagName("date").item(0).getTextContent();
                String location = element.getElementsByTagName("location").item(0).getTextContent();
                String description = element.getElementsByTagName("description").item(0).getTextContent();
                String status = element.getElementsByTagName("status").item(0).getTextContent();

                Project project = new Project(id, projectName, date, location, description, status);
                projects.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }
}
