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
    private String datetime;
    private String itemArabic;
    private String itemDescriptionArabic;

    public FavoriteModel() {
    }

    public FavoriteModel(String item, String itemDescription, String itemArabic, String itemDescriptionArabic,
                         String url, int pageId,String datetime) {
        this.item = item;
        this.itemDescription = itemDescription;
        this.itemArabic = itemArabic;
        this.itemDescriptionArabic = itemDescriptionArabic;
        this.url = url;
        this.pageId = pageId;
        this.datetime = datetime;
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

    public String getItemArabic() {
        return itemArabic;
    }

    public void setItemArabic(String itemArabic) {
        this.itemArabic = itemArabic;
    }

    public String getItemDescriptionArabic() {
        return itemDescriptionArabic;
    }

    public void setItemDescriptionArabic(String itemDescriptionArabic) {
        this.itemDescriptionArabic = itemDescriptionArabic;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
