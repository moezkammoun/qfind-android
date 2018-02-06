package qfind.com.qfindappandroid.favoritePage;

/**
 * Created by MoongedePC on 23-Jan-18.
 */

public class FavoriteModel {

    int id;
    private String item;
    private String itemDescription;
    private String url;
    private int pageId;

    public FavoriteModel() {
    }

    public FavoriteModel(String item, String itemDescription, String url,int pageId) {
        this.item = item;
        this.itemDescription = itemDescription;
        this.url = url;
        this.pageId=pageId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}
