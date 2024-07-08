package progress;

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

public class ProgressFormService {

    private File xmlFile;

    public ProgressFormService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public void submitReport(ProgressForm reportForm) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Find the root element
            Element rootElement = doc.getDocumentElement();

            // Generate new report ID
            NodeList progressList = rootElement.getElementsByTagName("progress");
            int newId = progressList.getLength() + 1;

            // Create new progress element
            Element newProgress = doc.createElement("progress");

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            newProgress.appendChild(idElement);

            Element projectNameElement = doc.createElement("projectName");
            projectNameElement.appendChild(doc.createTextNode(reportForm.getProjectName()));
            newProgress.appendChild(projectNameElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(reportForm.getDate()));
            newProgress.appendChild(dateElement);

            Element locationElement = doc.createElement("location");
            locationElement.appendChild(doc.createTextNode(reportForm.getLocation()));
            newProgress.appendChild(locationElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(reportForm.getDescription()));
            newProgress.appendChild(descriptionElement);

            rootElement.appendChild(newProgress);

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
