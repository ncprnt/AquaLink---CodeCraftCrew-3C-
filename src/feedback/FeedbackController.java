package feedback;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class FeedbackController {

    @FXML
    private TableView<Feedback> tableMyReports;

    @FXML
    private TableColumn<Feedback, Integer> colReportId1;

    @FXML
    private TableColumn<Feedback, String> colReportId;

    @FXML
    private TableColumn<Feedback, LocalDate> colReportDate;

    @FXML
    private TableColumn<Feedback, String> colReportLocation;

    @FXML
    private TableColumn<Feedback, String> colReportDescription;

    private FeedbackService feedbackService;

    public FeedbackController() {
        this.feedbackService = new FeedbackService();
    }

    public void initialize() {
        colReportId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReportId.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colReportDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReportLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colReportDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadFeedbackData();
    }

    private void loadFeedbackData() {
        List<Feedback> feedbackList = feedbackService.loadFeedbackFromXML();
        tableMyReports.getItems().setAll(feedbackList);
    }
}
