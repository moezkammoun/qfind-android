package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 28-Jan-18.
 */

public class HistoryItem {
    int id;
    String day;
    String titke;
    String image;
    String description;

    public HistoryItem() {
    }

    public HistoryItem(int id) {
        this.id = id;
    }

    public HistoryItem(String day, String titke, String image, String description) {
        this.day = day;
        this.titke = titke;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitke() {
        return titke;
    }

    public void setTitke(String titke) {
        this.titke = titke;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
