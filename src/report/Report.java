package report;

public class Report {
    private int id;
    private String title;
    private String date;
    private String location;
    private String description;
    private String status;

    public Report(int id, String title, String date, String location, String description, String status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
