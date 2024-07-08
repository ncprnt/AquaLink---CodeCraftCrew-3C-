package article;

public class ArticleForm {
    private String title;
    private String imagePath;
    private String upperParagraph;
    private String lowerParagraph;

    public ArticleForm(String title, String imagePath, String upperParagraph, String lowerParagraph) {
        this.title = title;
        this.imagePath = imagePath;
        this.upperParagraph = upperParagraph;
        this.lowerParagraph = lowerParagraph;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUpperParagraph() {
        return upperParagraph;
    }

    public void setUpperParagraph(String upperParagraph) {
        this.upperParagraph = upperParagraph;
    }

    public String getLowerParagraph() {
        return lowerParagraph;
    }

    public void setLowerParagraph(String lowerParagraph) {
        this.lowerParagraph = lowerParagraph;
    }
}
