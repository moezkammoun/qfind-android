package qfind.com.qfindappandroid.categoryfragment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dilee on 26-01-2018.
 */

public class SubCategory {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private ArrayList<SubCategoryItemList> subCategoryItemList;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public  ArrayList<SubCategoryItemList> getSubCategoryItemList(){
        return subCategoryItemList;
    }
}
