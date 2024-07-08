package proposal;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProposalController {

    @FXML
    private TableView<Proposal> tableMyProposals;

    @FXML
    private TableColumn<Proposal, Integer> colProposalId;

    @FXML
    private TableColumn<Proposal, String> colProposalTitle;

    @FXML
    private TableColumn<Proposal, String> colProposalDate;

    @FXML
    private TableColumn<Proposal, String> colProposalLocation;

    @FXML
    private TableColumn<Proposal, String> colProposalDescription;

    @FXML
    private TableColumn<Proposal, String> colProposalStatus;

    private ProposalService proposalService;

    public ProposalController() {
        proposalService = new ProposalService("src/database/Proposal.xml");
    }

    @FXML
    public void initialize() {
        colProposalId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProposalTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colProposalDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProposalLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colProposalDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProposalStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadProposals();
    }

    private void loadProposals() {
        try {
            List<Proposal> proposals = proposalService.loadProposalsFromXML();
            tableMyProposals.getItems().setAll(proposals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
