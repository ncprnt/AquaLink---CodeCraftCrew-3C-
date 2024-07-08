package project;

public class ProjectForm {
    private int id;
    private String projectName;
    private String date;
    private String location;
    private String description;
    private String status;

    public ProjectForm(int id, String projectName, String date, String location, String description, String status) {
        this.id = id;
        this.projectName = projectName;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
