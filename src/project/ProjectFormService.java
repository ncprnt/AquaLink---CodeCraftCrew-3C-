package project;

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

public class ProjectFormService {

    private File xmlFile;

    public ProjectFormService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public void submitProject(ProjectForm projectForm) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Find the root element
            Element rootElement = doc.getDocumentElement();

            // Generate new project ID
            NodeList projectList = rootElement.getElementsByTagName("project");
            int newId = projectList.getLength() + 1;

            // Create new project element
            Element newProject = doc.createElement("project");

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            newProject.appendChild(idElement);

            Element projectNameElement = doc.createElement("projectName");
            projectNameElement.appendChild(doc.createTextNode(projectForm.getProjectName()));
            newProject.appendChild(projectNameElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(projectForm.getDate()));
            newProject.appendChild(dateElement);

            Element locationElement = doc.createElement("location");
            locationElement.appendChild(doc.createTextNode(projectForm.getLocation()));
            newProject.appendChild(locationElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(projectForm.getDescription()));
            newProject.appendChild(descriptionElement);

            Element statusElement = doc.createElement("status");
            statusElement.appendChild(doc.createTextNode(projectForm.getStatus()));
            newProject.appendChild(statusElement);

            rootElement.appendChild(newProject);

            // Save the updated document without indentation
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no"); // Set indentation to "no"
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no"); // Preserve XML declaration
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception or show error message
        }
    }
}
