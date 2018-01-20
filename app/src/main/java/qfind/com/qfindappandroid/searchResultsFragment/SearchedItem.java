package qfind.com.qfindappandroid.searchResultsFragment;

public class SearchedItem {
    private String item;
    private String itemDescription;
    private int thumbnail;

    public SearchedItem() {
    }

    public SearchedItem(String item, String itemDescription, int thumbnail) {
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

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

}
