package proposal;

import java.time.LocalDate;

public class ProposalForm {
    private String title;
    private LocalDate date;
    private String location;
    private String description;
    private String status; // Add status field

    public ProposalForm(String title, LocalDate date, String location, String description, String status) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = status; // Initialize status
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status; // Getter for status
    }
}
