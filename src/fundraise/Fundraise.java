package fundraise;

import java.time.LocalDate;

public class Fundraise {

    private int id;
    private String title;
    private LocalDate deadline;
    private String description;

    public Fundraise(int id, String title, LocalDate deadline, String description) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Fundraise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                '}';
    }
}
