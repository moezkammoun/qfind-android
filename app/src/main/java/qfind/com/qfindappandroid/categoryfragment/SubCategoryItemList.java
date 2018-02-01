package qfind.com.qfindappandroid.categoryfragment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dilee on 26-01-2018.
 */

public class SubCategoryItemList {
    @SerializedName("sub_categories_id")
    private int subCategoryId;
    @SerializedName("sub_categories_name")
    private String subCategoryName;
    @SerializedName("sub_categories_imge")
    private String subCategoryImage;

    public SubCategoryItemList(int subCategoryId, String subCategoryName, String subCategoryImage) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryImage = subCategoryImage;
    }

    public int getSubCategoryId(){return subCategoryId;}
    public String getSubCategoryName(){return subCategoryName;}
    public String getSubCategoryImage(){return subCategoryImage;}
}
