package fundraise;

public class JoinFundraise {

    private int id;
    private String projectTitle; // Added project title
    private String fullName;
    private double donationAmount;

    public JoinFundraise(int id, String projectTitle, String fullName, double donationAmount) {
        this.id = id;
        this.projectTitle = projectTitle;
        this.fullName = fullName;
        this.donationAmount = donationAmount;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    @Override
    public String toString() {
        return "JoinFundraise{" +
                "id=" + id +
                ", projectTitle='" + projectTitle + '\'' +
                ", fullName='" + fullName + '\'' +
                ", donationAmount=" + donationAmount +
                '}';
    }
}
