package feedback;

import java.time.LocalDate;

public class Feedback {
    private int id;
    private String fullName;
    private String projectName;
    private LocalDate date;
    private String location;
    private String description;

    public Feedback(int id, String fullName, String projectName, LocalDate date, String location, String description) {
        this.id = id;
        this.fullName = fullName;
        this.projectName = projectName;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
