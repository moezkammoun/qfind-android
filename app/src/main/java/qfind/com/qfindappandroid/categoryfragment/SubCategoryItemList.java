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
    @SerializedName("sub_categories_description")
    private String subCategoryDescription;

    public SubCategoryItemList(int subCategoryId, String subCategoryName, String subCategoryImage, String subCategoryDescription) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryImage = subCategoryImage;
        this.subCategoryDescription = subCategoryDescription;
    }

    public int getSubCategoryId(){return subCategoryId;}
    public String getSubCategoryName(){return subCategoryName;}
    public String getSubCategoryImage(){return subCategoryImage;}
    public String getSubCategoryDescription(){return subCategoryDescription;}
}
