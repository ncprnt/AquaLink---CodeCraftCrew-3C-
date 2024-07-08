package proposal;

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

public class ProposalFormService {

    private File xmlFile;

    public ProposalFormService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public void saveProposal(ProposalForm proposalForm) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Find the root element
            Element rootElement = doc.getDocumentElement();

            // Generate new proposal ID
            NodeList proposalList = rootElement.getElementsByTagName("proposal");
            int newId = proposalList.getLength() + 1;

            // Create new proposal element
            Element newProposal = doc.createElement("proposal");

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            newProposal.appendChild(idElement);

            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode(proposalForm.getTitle()));
            newProposal.appendChild(titleElement);

            Element dateElement = doc.createElement("date");
            dateElement.appendChild(doc.createTextNode(proposalForm.getDate().toString()));
            newProposal.appendChild(dateElement);

            Element locationElement = doc.createElement("location");
            locationElement.appendChild(doc.createTextNode(proposalForm.getLocation()));
            newProposal.appendChild(locationElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(proposalForm.getDescription()));
            newProposal.appendChild(descriptionElement);

            Element statusElement = doc.createElement("status");
            statusElement.appendChild(doc.createTextNode(proposalForm.getStatus()));
            newProposal.appendChild(statusElement);

            rootElement.appendChild(newProposal);

            // Save the updated document
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
