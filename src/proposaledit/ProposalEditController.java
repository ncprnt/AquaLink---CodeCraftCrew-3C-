package proposaledit;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class ProposalEditController {

    @FXML
    private TableView<ProposalEdit> tableMyProposals;

    @FXML
    private TableColumn<ProposalEdit, Integer> colProposalId;

    @FXML
    private TableColumn<ProposalEdit, String> colProposalTitle;

    @FXML
    private TableColumn<ProposalEdit, String> colProposalDate;

    @FXML
    private TableColumn<ProposalEdit, String> colProposalLocation;

    @FXML
    private TableColumn<ProposalEdit, String> colProposalDescription;

    @FXML
    private TableColumn<ProposalEdit, String> colProposalStatus;

    @FXML
    private TableColumn<ProposalEdit, String> colAction;

    private ProposalEditService proposalService;

    public ProposalEditController() {
        proposalService = new ProposalEditService("src/database/Proposal.xml");
    }

    @FXML
    public void initialize() {
        colProposalId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProposalTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colProposalDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProposalLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colProposalDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProposalStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Enable editing for the status column
        tableMyProposals.setEditable(true);
        colProposalStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add custom action column
        colAction.setCellFactory(actionCellFactory);

        loadProposals();
    }

    private void loadProposals() {
        try {
            List<ProposalEdit> proposals = proposalService.loadProposalsFromXML();
            tableMyProposals.getItems().setAll(proposals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Callback<TableColumn<ProposalEdit, String>, TableCell<ProposalEdit, String>> actionCellFactory =
            new Callback<>() {
                @Override
                public TableCell<ProposalEdit, String> call(final TableColumn<ProposalEdit, String> param) {
                    final TableCell<ProposalEdit, String> cell = new TableCell<>() {

                        final ComboBox<String> comboBox = new ComboBox<>();
                        final Button deleteButton = new Button("Delete");

                        {
                            comboBox.setItems(FXCollections.observableArrayList("Pending", "Accepted", "Rejected"));
                            comboBox.setStyle("-fx-background-color: white; -fx-border-color: #c9c9c9; -fx-border-radius: 10; -fx-background-radius: 10;");
                            comboBox.setOnAction(event -> {
                                ProposalEdit proposal = getTableView().getItems().get(getIndex());
                                String newStatus = comboBox.getValue();
                                proposal.setStatus(newStatus);
                                proposalService.updateProposalStatus(proposal);
                                tableMyProposals.refresh(); // Refresh the table to show the updated status
                            });

                            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10;");
                            deleteButton.setOnAction(event -> {
                                ProposalEdit proposal = getTableView().getItems().get(getIndex());
                                proposalService.deleteProposal(proposal.getId());
                                tableMyProposals.getItems().remove(proposal);
                            });
                        }

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                ProposalEdit proposal = getTableView().getItems().get(getIndex());
                                comboBox.setValue(proposal.getStatus());
                                HBox hbox = new HBox(10, comboBox, deleteButton); // Add spacing
                                hbox.setSpacing(10); // Set spacing between ComboBox and Button
                                setGraphic(hbox);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };
}
