package projectedit;

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
import java.util.ArrayList;
import java.util.List;

public class ProjectEditService {

    private File xmlFile;

    public ProjectEditService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public List<ProjectEdit> loadProjectsFromXML() {
        List<ProjectEdit> projects = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
    
            NodeList nodeList = doc.getElementsByTagName("project");
    
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
    
                // Ensure elements exist and are not null
                if (element.getElementsByTagName("id").item(0) == null ||
                    element.getElementsByTagName("projectName").item(0) == null ||
                    element.getElementsByTagName("date").item(0) == null ||
                    element.getElementsByTagName("location").item(0) == null ||
                    element.getElementsByTagName("description").item(0) == null ||
                    element.getElementsByTagName("status").item(0) == null) {
                    System.out.println("Missing element in project node at index: " + i);
                    continue;
                }
    
                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                String projectName = element.getElementsByTagName("projectName").item(0).getTextContent();
                String date = element.getElementsByTagName("date").item(0).getTextContent();
                String location = element.getElementsByTagName("location").item(0).getTextContent();
                String description = element.getElementsByTagName("description").item(0).getTextContent();
                String status = element.getElementsByTagName("status").item(0).getTextContent();
    
                projects.add(new ProjectEdit(id, projectName, date, location, description, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return projects;
    }
    

    public void updateProjectStatus(ProjectEdit project) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("project");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());

                if (id == project.getId()) {
                    element.getElementsByTagName("status").item(0).setTextContent(project.getStatus());
                    break;
                }
            }

            // Save changes to XML file
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

    public void deleteProject(int projectId) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("project");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());

                if (id == projectId) {
                    element.getParentNode().removeChild(element);
                    break;
                }
            }

            // Save changes to XML file
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
