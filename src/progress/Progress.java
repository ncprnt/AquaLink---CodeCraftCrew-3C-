package progress;

public class Progress {
    private int id;
    private String projectName;
    private String date;
    private String location;
    private String description;

    public Progress(int id, String projectName, String date, String location, String description) {
        this.id = id;
        this.projectName = projectName;
        this.date = date;
        this.location = location;
        this.description = description;
    }

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
}
