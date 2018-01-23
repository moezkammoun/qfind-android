package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 22-Jan-18.
 */

public class HistoryItem {
    private String item;
    private String itemDescription;
    private int thumbnail;

    public HistoryItem() {
    }

    public HistoryItem(String item, String itemDescription, int thumbnail) {
        this.item = item;
        this.itemDescription = itemDescription;
        this.thumbnail = thumbnail;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
