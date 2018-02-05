package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageDataModel {

    int id;
    String pageName;
    String url;
    int images;
    String description;

    public HistoryPageDataModel() {
    }

    public HistoryPageDataModel(String pageName, int images, String description) {
        this.pageName = pageName;
        this.images = images;
        this.description = description;
    }

    public HistoryPageDataModel(String pageName, String url, String description) {
        this.pageName = pageName;
        this.url = url;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getImages() {
//        return images;
//    }
//
//    public void setImages(int images) {
//        this.images = images;
//    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
