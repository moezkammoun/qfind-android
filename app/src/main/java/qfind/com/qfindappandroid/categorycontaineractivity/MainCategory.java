package qfind.com.qfindappandroid.categorycontaineractivity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dilee on 26-01-2018.
 */

public class MainCategory {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private ArrayList<MainCategoryItemList> mainCategoryItemList;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public  ArrayList<MainCategoryItemList> getMainCategoryItemList(){
        return mainCategoryItemList;
    }
}
