package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageDataModel {

    String pageName;
    //    String url;
    int images;

    public HistoryPageDataModel() {
    }

    public HistoryPageDataModel(String pageName, int images) {
        this.pageName = pageName;
        this.images = images;
    }

//    public HistoryPageDataModel(String pageName, String url, int images) {
//        this.pageName = pageName;
//        this.url = url;
//        this.images = images;
//    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}
