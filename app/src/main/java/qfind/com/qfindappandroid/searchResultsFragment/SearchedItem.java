package qfind.com.qfindappandroid.searchResultsFragment;

public class SearchedItem {
    private String item;
    private String itemDescription;
    private String thumbnail;

    public SearchedItem() {
    }

    public SearchedItem(String item, String itemDescription, String thumbnail) {
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

}
