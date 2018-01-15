package qfind.com.qfindappandroid.categoryfragment;

/**
 * Created by dilee on 07-01-2018.
 */

public class Categories {
    private String item;
    private int thumbnail;

    public Categories() {
    }

    public Categories(String item, int thumbnail) {
        this.item = item;
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
}
