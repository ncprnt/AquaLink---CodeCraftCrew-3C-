package fundraise;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FundraiseTableViewController {

    @FXML
    private TableView<Fundraise> fundraiseTable;

    @FXML
    private TableColumn<Fundraise, Integer> colFundraiseId;

    @FXML
    private TableColumn<Fundraise, String> colFundraiseTitle;

    @FXML
    private TableColumn<Fundraise, LocalDate> colFundraiseDeadline;

    @FXML
    private TableColumn<Fundraise, String> colFundraiseDescription;

    private static final String XML_FILE_PATH = "src/database/Fundraise.xml";

    @FXML
    public void initialize() {
        colFundraiseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFundraiseTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colFundraiseDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        colFundraiseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load the fundraise data from XML
        loadFundraisesFromXML();
    }

    private void loadFundraisesFromXML() {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("fundraise");
                List<Fundraise> fundraises = new ArrayList<>();

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                        String title = element.getElementsByTagName("title").item(0).getTextContent();
                        LocalDate deadline = LocalDate.parse(element.getElementsByTagName("deadline").item(0).getTextContent());
                        String description = element.getElementsByTagName("description").item(0).getTextContent();

                        Fundraise fundraise = new Fundraise(id, title, deadline, description);
                        fundraises.add(fundraise);
                    }
                }

                fundraiseTable.getItems().setAll(fundraises);
            } else {
                System.err.println("XML file not found: " + XML_FILE_PATH);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
