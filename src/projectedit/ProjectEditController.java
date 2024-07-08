package projectedit;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class ProjectEditController {

    @FXML
    private TableView<ProjectEdit> tableProjects;

    @FXML
    private TableColumn<ProjectEdit, Integer> colProjectId;

    @FXML
    private TableColumn<ProjectEdit, String> colProjectName;

    @FXML
    private TableColumn<ProjectEdit, String> colProjectDate;

    @FXML
    private TableColumn<ProjectEdit, String> colProjectLocation;

    @FXML
    private TableColumn<ProjectEdit, String> colProjectDescription;

    @FXML
    private TableColumn<ProjectEdit, String> colProjectStatus;

    @FXML
    private TableColumn<ProjectEdit, String> colAction;

    private ProjectEditService projectService;

    public ProjectEditController() {
        projectService = new ProjectEditService("src/database/Project.xml");
    }

    @FXML
    public void initialize() {
        colProjectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProjectLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colProjectDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Enable editing for the status column
        tableProjects.setEditable(true);
        colProjectStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add custom action column
        colAction.setCellFactory(actionCellFactory);

        loadProjects();
    }

    private void loadProjects() {
        try {
            List<ProjectEdit> projects = projectService.loadProjectsFromXML();
            tableProjects.getItems().setAll(projects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Callback<TableColumn<ProjectEdit, String>, TableCell<ProjectEdit, String>> actionCellFactory =
            new Callback<>() {
                @Override
                public TableCell<ProjectEdit, String> call(final TableColumn<ProjectEdit, String> param) {
                    final TableCell<ProjectEdit, String> cell = new TableCell<>() {

                        final ComboBox<String> comboBox = new ComboBox<>();
                        final Button deleteButton = new Button("Delete");

                        {
                            comboBox.setItems(FXCollections.observableArrayList("Pending", "On Going", "Completed"));
                            comboBox.setStyle("-fx-background-color: white; -fx-border-color: #c9c9c9; -fx-border-radius: 10; -fx-background-radius: 10;");
                            comboBox.setOnAction(event -> {
                                ProjectEdit project = getTableView().getItems().get(getIndex());
                                String newStatus = comboBox.getValue();
                                project.setStatus(newStatus);
                                projectService.updateProjectStatus(project);
                                tableProjects.refresh(); // Refresh the table to show the updated status
                            });

                            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10;");
                            deleteButton.setOnAction(event -> {
                                ProjectEdit project = getTableView().getItems().get(getIndex());
                                projectService.deleteProject(project.getId());
                                tableProjects.getItems().remove(project);
                            });
                        }

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                ProjectEdit project = getTableView().getItems().get(getIndex());
                                comboBox.setValue(project.getStatus());
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
