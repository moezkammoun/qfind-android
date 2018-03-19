package qfind.com.qfindappandroid.categorycontaineractivity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dilee on 26-01-2018.
 */

public class MainCategoryItemList {
    @SerializedName("categories_id")
    private int categoryId;
    @SerializedName("categories_name")
    private String categoryName;
    @SerializedName("categories_imge")
    private String categoryImage;
    @SerializedName("have_subcategories")
    private boolean subCategoryStatus;

    public MainCategoryItemList(int categoryId, String categoryName, String categoryImage, boolean subCategoryStatus) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.subCategoryStatus = subCategoryStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public boolean getSubCategoryStatus() {
        return subCategoryStatus;
    }
}
