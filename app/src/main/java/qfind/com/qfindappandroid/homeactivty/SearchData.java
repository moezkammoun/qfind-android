package qfind.com.qfindappandroid.homeactivty;

import com.google.gson.annotations.SerializedName;


public class SearchData {
    @SerializedName("search_name")
    private String searchName;
    @SerializedName("search_type")
    private Integer searchType;
    @SerializedName("item_id")
    private Integer itemId;
    @SerializedName("message")
    private String message;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
