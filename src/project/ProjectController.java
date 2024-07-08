package project;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProjectController {

    @FXML
    private TableView<Project> tableProjects;

    @FXML
    private TableColumn<Project, Integer> colProjectId;

    @FXML
    private TableColumn<Project, String> colProjectName;

    @FXML
    private TableColumn<Project, String> colProjectDate;

    @FXML
    private TableColumn<Project, String> colProjectLocation;

    @FXML
    private TableColumn<Project, String> colProjectDescription;

    @FXML
    private TableColumn<Project, String> colProjectStatus;

    private ProjectService projectService;

    @FXML
    public void initialize() {
        projectService = new ProjectService("src/database/Project.xml");

        colProjectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProjectName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colProjectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colProjectLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colProjectDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load projects from XML and add them to the table
        List<Project> projects = projectService.loadProjectsFromXML();
        tableProjects.getItems().setAll(projects);
    }
}
