package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageDataModel {

    int id;
    String pageName;
    String url;
    String description;

    public HistoryPageDataModel() {
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
