package progress;

public class ProgressForm {
    private int id;
    private String projectname;
    private String date;
    private String location;
    private String description;
    private String status;

    public ProgressForm(int id, String projectname, String date, String location, String description, String status) {
        this.id = id;
        this.projectname = projectname;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    public ProgressForm() {}

    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectname;
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
