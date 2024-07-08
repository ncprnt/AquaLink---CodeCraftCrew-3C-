package report;

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

public class ReportFormService {

    private File xmlFile;

    public ReportFormService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public void submitReport(ReportForm reportForm) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Find the root element
            Element rootElement = doc.getDocumentElement();

            // Generate new report ID
            NodeList reportList = rootElement.getElementsByTagName("report");
            int newId = reportList.getLength() + 1;

            // Create new report element
            Element newReport = doc.createElement("report");

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            newReport.appendChild(idElement);

            Element statusElement = doc.createElement("status");
            statusElement.appendChild(doc.createTextNode(reportForm.getStatus()));
            newReport.appendChild(statusElement);

            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode(reportForm.getTitle()));
            newReport.appendChild(titleElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(reportForm.getDate()));
            newReport.appendChild(dateElement);

            Element locationElement = doc.createElement("location");
            locationElement.appendChild(doc.createTextNode(reportForm.getLocation()));
            newReport.appendChild(locationElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(reportForm.getDescription()));
            newReport.appendChild(descriptionElement);

            rootElement.appendChild(newReport);

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
