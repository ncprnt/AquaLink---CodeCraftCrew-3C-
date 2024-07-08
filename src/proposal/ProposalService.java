package proposal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProposalService {

    private File xmlFile;

    public ProposalService(String filePath) {
        this.xmlFile = new File(filePath);
    }

    public List<Proposal> loadProposalsFromXML() {
        List<Proposal> proposals = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("proposal");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String date = element.getElementsByTagName("date").item(0).getTextContent();
                String location = element.getElementsByTagName("location").item(0).getTextContent();
                String description = element.getElementsByTagName("description").item(0).getTextContent();
                String status = element.getElementsByTagName("status").item(0).getTextContent();

                Proposal proposal = new Proposal(id, title, date, location, description, status);
                proposals.add(proposal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return proposals;
    }
}
